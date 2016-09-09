--DROP TABLE public.puesto;
--DROP TABLE public.sector_puesto;
--DROP TABLE public.rol_func;
--DROP TABLE public.puesto_transf;
--DROP TABLE public.display;
--DROP TABLE public.sector;
--DROP TABLE public.funcionario;
--DROP TABLE public.rol;

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE public.sector
(
  SectorID serial,
  Nombre character varying(40),
  CONSTRAINT sector_pkey PRIMARY KEY (SectorID)
);

CREATE TABLE public.display
(
  NombrePC character varying(40),
  DirArchivo character varying(40),
  Sector int references sector(SectorID), --relacion ONETOMANY entre Sector y Display
  CONSTRAINT display_pkey PRIMARY KEY (NombrePC)
);

CREATE TABLE public.funcionario
(
  Login character varying(40),
  Password character varying(40),
  Nombre character varying(40),
  Apellido character varying(40),
  CorreoElectronico character varying(40),
  CONSTRAINT funcionario_pkey PRIMARY KEY (Login)
);

CREATE TABLE public.puesto
(
  Codigo serial,
  Estado character varying(40),
  IsAbierto boolean,
  Funcionario character varying(40) references funcionario(Login), --relacion ONETOMANY entre Funcionario y Puesto
  CONSTRAINT puesto_pkey PRIMARY KEY (codigo)
);

CREATE TABLE public.rol
(
  Nombre character varying(40),
  CONSTRAINT rol_pkey PRIMARY KEY (Nombre)
);

CREATE TABLE public.tramite
(
  Codigo serial,
  Nombre character varying(40),
  CONSTRAINT tramite_pkey PRIMARY KEY (Codigo)
);

CREATE TABLE public.numero
(
  Numero int,
  Serie character varying(40),
  Emitido character varying(40),
  Estado character varying(40),
  EsSAE boolean,
  SeRedirecciona boolean,
  Prioridad int,
  TiempoDeAtendido int,  
  Puesto int references puesto(codigo), --relacion ONETOMANY entre Numero y Puesto
  Tramite int references tramite(codigo), --relacion ONETOMANY entre Tramite y Puesto
  CONSTRAINT numero_pkey PRIMARY KEY (Numero)
);

-- Tabla para modelar la relacion MANYTOMANY entre Funcionarios y Roles 
CREATE TABLE public.rol_func
(
  Rol character varying(40) references rol(Nombre),
  Funcionario character varying(40) references funcionario(Login)
);

-- Tabla para modelar la relacion MANYTOMANY entre Sectores y Puestos
CREATE TABLE public.sector_puesto
(
  Sector int references sector(SectorID),
  Puesto int references puesto(Codigo)
);


-- Tabla para modelar transferencias entre puestos
CREATE TABLE public.puesto_transf
(
  PuestoTransfiere int references puesto(Codigo),
  PuestoRecibe int references puesto(Codigo)
);

-- Tabla para modelar la relacion MANYTOMANY entre Sectores y Puestos
CREATE TABLE public.tramite_puesto
(
  Tramite int references tramite(Codigo),
  Puesto int references puesto(Codigo)
);



---------------
--select * from funcionario;
--select * from puesto




