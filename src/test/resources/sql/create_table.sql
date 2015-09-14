CREATE TABLE person_info (
  id BIGINT IDENTITY,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  age INTEGER,
  birth_date DATE NOT NULL,
  gender CHAR
);
