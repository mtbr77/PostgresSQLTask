CREATE TABLE IF NOT EXISTS cell_1 ( CHECK ( lon < 55 ) ) INHERITS (cell);
CREATE TABLE IF NOT EXISTS cell_2 ( CHECK ( lon >= 55 )) INHERITS (cell);

CREATE OR REPLACE FUNCTION lon_func_insert_trigger() RETURNS trigger AS '
BEGIN
  IF ( NEW.lon < 55 ) THEN INSERT INTO cell_1 VALUES (NEW.*);
  ELSIF ( NEW.lon >= 55 ) THEN INSERT INTO cell_2 VALUES (NEW.*);
  ELSE RAISE EXCEPTION ''Date out of range.  Fix the measurement_insert_trigger() function!'';
  END IF;
  RETURN NULL;
END;
' LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_cell_insert
  ON cell;

CREATE TRIGGER trigger_cell_insert
  BEFORE INSERT
  ON cell
  FOR EACH ROW
    EXECUTE PROCEDURE lon_func_insert_trigger();