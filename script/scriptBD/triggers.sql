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
			EXECUTE 'INSERT into metricas_estado_numero
				values('''|| NEW.internal_id ||''','''|| NEW.estado ||''')';

			EXECUTE 'UPDATE metricas_estado_numero SET
				time_spent = age(now(), date_created),
				last_updated = now()
				WHERE internal_id = '''|| NEW.internal_id ||''' AND estado = '''|| OLD.estado||''' AND time_spent is NULL';							
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