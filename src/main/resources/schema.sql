CREATE TABLE IF NOT EXISTS cell
(
  id bigint NOT NULL,
  area integer NOT NULL,
  average_signal integer NOT NULL,
  cell integer NOT NULL,
  changeable integer NOT NULL,
  created integer NOT NULL,
  lat double precision NOT NULL,
  lon double precision NOT NULL,
  mcc integer NOT NULL,
  net integer NOT NULL,
  radio character varying(255),
  range integer NOT NULL,
  samples integer NOT NULL,
  unit integer NOT NULL,
  updated bigint NOT NULL,
  CONSTRAINT cell_pkey PRIMARY KEY (id)
);