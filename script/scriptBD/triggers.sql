-- INSERT EN PUESTO
CREATE OR REPLACE FUNCTION nueva_metrica_puesto() RETURNS TRIGGER AS $$
    BEGIN
	EXECUTE 'INSERT into metricas_puesto
		values('''|| NEW.nombre_maquina ||''','''|| NEW.usuario_id ||''',''CERRADO'')'; 
        RETURN NULL;
    END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER nueva_metrica_puesto AFTER INSERT ON puesto
    FOR EACH ROW EXECUTE PROCEDURE nueva_metrica_puesto();
	
-- UPDATE ESTADO EN PUESTO
CREATE OR REPLACE FUNCTION actualizo_metrica_puesto() RETURNS TRIGGER AS $$
    BEGIN
		IF(OLD.estado != NEW.estado) THEN
			EXECUTE 'INSERT into metricas_puesto
				values('''|| NEW.nombre_maquina ||''','''|| NEW.usuario_id ||''','''|| NEW.estado ||''')';

			EXECUTE 'UPDATE metricas_puesto SET
				time_spent = age(now(), date_created),
				last_updated = now()
				WHERE codigo_puesto = '''|| NEW.nombre_maquina ||''' AND estado = '''|| OLD.estado||'''';							
		END IF;
		RETURN NULL;
    END;	
$$ LANGUAGE plpgsql;
CREATE TRIGGER actualizo_metrica_puesto AFTER UPDATE ON puesto
	FOR EACH ROW EXECUTE PROCEDURE actualizo_metrica_puesto();