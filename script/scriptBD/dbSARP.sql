BEGIN;

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

--------------------------------------------------------ENTIDADES--------------------------------------------------------
CREATE TABLE public.DISPLAY
(
  id_display character varying(40),
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,
  CONSTRAINT display_pkey PRIMARY KEY (id_display)
);

CREATE TABLE public.SECTOR
(
  codigo character varying(40),
  nombre character varying(40),
  ruta_sector character varying(40),  
  habilitado boolean default true,
  es_hoja boolean,
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,
  CONSTRAINT sector_pkey PRIMARY KEY (codigo)
);

CREATE TABLE public.TRAMITE
(
  codigo character varying(40),
  nombre character varying(40),
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,
  CONSTRAINT tramite_pkey PRIMARY KEY (codigo)
);

CREATE TABLE public.NUMERO
(
  internal_id serial,
  codigo_tramite character varying(40) references TRAMITE(codigo), --relacion ONETOMANY entre NUMERO y TRAMITE
  codigo_sector character varying(40) references SECTOR(codigo), --relacion ONETOMANY entre NUMERO y SECTOR
  puesto_asignado character varying(40), -- relacion ONETOONE entre NUMERO y PUESTO
  external_id character varying(40),
  hora timestamp,
  estado character varying(40) default 'PENDIENTE',
  prioridad int,
  fue_atrasado boolean default false,
  desvio int, --relacion ONETOONE entre NUMERO Y NUMERO
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,  
  CONSTRAINT numero_pkey PRIMARY KEY (internal_id)
);
ALTER TABLE NUMERO ADD FOREIGN KEY(desvio) REFERENCES NUMERO(internal_id);

CREATE TABLE public.PUESTO
(
  nombre_maquina character varying(40),
  numero int,
  numero_puesto int references NUMERO(internal_id), -- relacion ONETOONE entre PUESTO y NUMERO
  estado character varying(40) default 'CERRADO',
  usuario_id character varying(40) default '-',
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,
  CONSTRAINT puesto_pkey PRIMARY KEY (nombre_maquina)
);
ALTER TABLE NUMERO ADD  FOREIGN KEY(puesto_asignado) REFERENCES PUESTO(nombre_maquina);

CREATE TABLE public.DATOS_COMPLEMENTARIOS
(
  doc_identidad character varying(40),
  internal_id int references NUMERO(internal_id), --relacion ONETOONE entre DATOS_COMPLEMENTARIOS y NUMERO
  nombre_completo character varying(40),
  tipo_doc character varying(40),  
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,
  CONSTRAINT datos_complementarios_pkey PRIMARY KEY (internal_id)
);

--------------------------------------------------------RELACIONES--------------------------------------------------------
--Relacion MANYTOMANY entre NUMERO y PUESTO
CREATE TABLE public.ATENCION
(
  nombre_maquina character varying(40) references PUESTO(nombre_maquina),
  internal_id int references NUMERO(internal_id),
  CONSTRAINT atencion_pkey PRIMARY KEY (nombre_maquina, internal_id)
);

--Relacion MANYTOMANY entre DISPLAY y SECTOR
CREATE TABLE public.DISPLAY_SECTOR
(
  codigo_display character varying(40) references DISPLAY(id_display),
  codigo_sector character varying(40) references SECTOR(codigo),
  CONSTRAINT display_sector_pkey PRIMARY KEY (codigo_display, codigo_sector)
);

--Relacion MANYTOMANY entre TRAMITE y SECTOR
CREATE TABLE public.TRAMITE_SECTOR
(
  codigo_tramite character varying(40) references TRAMITE(codigo),
  codigo_sector character varying(40) references SECTOR(codigo),
  CONSTRAINT tramite_sector_pkey PRIMARY KEY (codigo_tramite, codigo_sector)
);

--Relacion MANYTOMANY entre PUESTO y SECTOR
CREATE TABLE public.PUESTO_SECTOR
(
  nombre_maquina character varying(40) references PUESTO(nombre_maquina),
  codigo_sector character varying(40) references SECTOR(codigo),
  CONSTRAINT puesto_sector_pkey PRIMARY KEY (nombre_maquina, codigo_sector)
);

--Relacion MANYTOMANY entre PUESTO y TRAMITE
CREATE TABLE public.PUESTO_TRAMITE
(
  nombre_maquina character varying(40) references PUESTO(nombre_maquina),
  codigo_tramite character varying(40) references TRAMITE(codigo),
  CONSTRAINT puesto_tramite_pkey PRIMARY KEY (nombre_maquina, codigo_tramite)
);

--Relacion MANYTOMANY entre NUMERO y TRAMITE
CREATE TABLE public.NUMERO_TRAMITE
(
  internal_id int references NUMERO(internal_id),
  codigo_tramite character varying(40) references TRAMITE(codigo),
  resultado_final character varying(40),
  CONSTRAINT numero_tramite_pkey PRIMARY KEY (internal_id, codigo_tramite)
);

--------------------------------------------------------METRICAS--------------------------------------------------------
CREATE TABLE public.METRICAS_NUMERO
(
  internal_id int,
  external_id character varying(40),
  estado character varying(40) default 'PENDIENTE',
  codigo_tramite character varying(40),
  ruta_sector character varying(40),
  usuario_atencion character varying(40),
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,  
  CONSTRAINT metricas_numero_pkey PRIMARY KEY (internal_id)
);

CREATE TABLE public.METRICAS_ESTADO_NUMERO
(
  internal_id int,
  estado character varying(40) default 'PENDIENTE',  
  time_spent character varying(40),
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,  
  CONSTRAINT metricas_estado_numero_pkey PRIMARY KEY (internal_id, estado,date_created)
);

CREATE TABLE public.METRICAS_PUESTO
(
  nombre_maquina character varying(40),
  usuario_atencion character varying(40) default '-',
  estado character varying(40) default 'CERRADO',
  time_spent character varying(40),
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,  
  CONSTRAINT metricas_puesto_pkey PRIMARY KEY (nombre_maquina, usuario_atencion,estado, date_created)
);

--------------------------------------------------------TRIGGERS--------------------------------------------------------
-- INSERT EN PUESTO
CREATE OR REPLACE FUNCTION nueva_metrica_puesto() RETURNS TRIGGER AS $$
    BEGIN
		INSERT INTO metricas_puesto values(NEW.nombre_maquina);
        RETURN NULL;
    END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER nueva_metrica_puesto AFTER INSERT ON puesto
    FOR EACH ROW EXECUTE PROCEDURE nueva_metrica_puesto();
	
-- UPDATE ESTADO EN PUESTO
CREATE OR REPLACE FUNCTION actualizo_metrica_puesto() RETURNS TRIGGER AS $$
    BEGIN
		IF(OLD.estado != NEW.estado) THEN
			INSERT INTO metricas_puesto values(NEW.nombre_maquina, NEW.usuario_id, NEW.estado);
			UPDATE metricas_puesto SET 
				time_spent = age(now(), date_created),
				last_updated = now()
				WHERE nombre_maquina = NEW.nombre_maquina AND estado = OLD.estado AND time_spent is NULL;							
		END IF;
		RETURN NULL;
    END;	
$$ LANGUAGE plpgsql;
CREATE TRIGGER actualizo_metrica_puesto AFTER UPDATE ON puesto
	FOR EACH ROW EXECUTE PROCEDURE actualizo_metrica_puesto();
	
-- INSERT EN NUMERO
CREATE OR REPLACE FUNCTION nueva_metrica_numero() RETURNS TRIGGER AS $$
DECLARE
    ruta character varying(40);
    BEGIN		
		--NUEVA METRICA_ESTADO_NUMERO
		INSERT INTO metricas_estado_numero values(NEW.internal_id, NEW.estado);
			
		--NUEVA METRICA-NUMERO
		SELECT ruta_sector FROM sector WHERE codigo = NEW.codigo_sector INTO ruta;
		INSERT INTO metricas_numero values(NEW.internal_id, NULL, NEW.estado, NEW.codigo_tramite, ruta);
	RETURN NULL;
    END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER nueva_metrica_numero AFTER INSERT ON numero
    FOR EACH ROW EXECUTE PROCEDURE nueva_metrica_numero();
	
-- UPDATE EN NUMERO
CREATE OR REPLACE FUNCTION actualizo_metrica_numero() RETURNS TRIGGER AS $$
DECLARE
    usuario character varying(40);
    BEGIN
		--ACTUALIZO METRICA_ESTADO_PUESTO
		IF(OLD.estado != NEW.estado) THEN
			INSERT INTO metricas_estado_numero VALUES(NEW.internal_id, NEW.estado); 

			UPDATE metricas_estado_numero SET
				time_spent = age(now(), date_created),
				last_updated = now()
				WHERE internal_id = NEW.internal_id AND estado = OLD.estado AND time_spent is NULL;
		END IF;
		--ACTUALIZO METRICA_NUMERO
		SELECT usuario_id FROM puesto WHERE nombre_maquina = NEW.puesto_asignado INTO usuario;
		UPDATE metricas_numero SET
			external_id = NEW.external_id,
			usuario_atencion = usuario
			WHERE internal_id = NEW.internal_id;
		RETURN NULL;
    END;	
$$ LANGUAGE plpgsql;
CREATE TRIGGER actualizo_metrica_numero AFTER UPDATE ON numero
	FOR EACH ROW EXECUTE PROCEDURE actualizo_metrica_numero();
	
--------------------------------------------------------TRAMITE DESVIAR--------------------------------------------------------
insert into tramite values('1', 'Tramite Generico');
CREATE OR REPLACE RULE tramite_desviar_delete_protect AS ON DELETE TO tramite 
	WHERE codigo = '1'
	DO INSTEAD NOTHING;
CREATE OR REPLACE RULE tramite_desviar_update_protect AS ON UPDATE TO tramite 
	WHERE old.codigo = '1' AND old.nombre != new.nombre
	DO INSTEAD NOTHING;

COMMIT;