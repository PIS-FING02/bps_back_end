BEGIN;

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

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
  habilitado boolean,
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,
  CONSTRAINT sector_pkey PRIMARY KEY (codigo)
);

CREATE TABLE public.TRAMITE
(
  codigo serial,
  nombre character varying(40),
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,
  CONSTRAINT tramite_pkey PRIMARY KEY (codigo)
);

CREATE TABLE public.NUMERO
(
  internal_id serial,
  codigo_tramite int references TRAMITE(codigo), --relacion ONETOMANY entre NUMERO y TRAMITE
  codigo_sector character varying(40) references SECTOR(codigo), --relacion ONETOMANY entre NUMERO y SECTOR
  puesto_asignado character varying(40), -- relacion ONETOONE entre NUMERO y PUESTO
  external_id character varying(40),
  hora timestamp,
  estado character varying(40) default 'PENDIENTE',
  prioridad int,
  fue_atrasado boolean default false,
  resultado_final character varying(40),
  date_created timestamp default current_timestamp,
  last_updated timestamp default current_timestamp,  
  CONSTRAINT numero_pkey PRIMARY KEY (internal_id)
);
--Trigger para insert/update en numero

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
--Trigger para insert/update en puesto


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
  codigo_tramite int references TRAMITE(codigo),
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
  codigo_tramite int references TRAMITE(codigo),
  CONSTRAINT puesto_tramite_pkey PRIMARY KEY (nombre_maquina, codigo_tramite)
);

CREATE TABLE public.METRICAS_NUMERO
(
  internal_id int,
  external_id character varying(40),
  estado character varying(40) default 'PENDIENTE',
  codigo_tramite int,
  ruta_sector character varying(40),
  usuario_atencion character varying(40),
  resultado_final character varying(40),
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

COMMIT;



