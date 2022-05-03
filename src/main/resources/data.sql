DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  birthdate DATE NOT NULL,
  country VARCHAR(255) NOT NULL,
  phone VARCHAR(20),
  gender VARCHAR(6) NOT NULL CHECK (Gender IN ('Male', 'Female'))
);
