-- EJECUTAR EN PGADMIN

-- Table: public.sectores

-- DROP TABLE public.sectores;

CREATE TABLE public.sectores
(
  id serial,
  nombre character varying(40) NOT NULL,
  CONSTRAINT sectores_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.sectores
  OWNER TO postgres;
