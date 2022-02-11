CREATE TABLE IF NOT EXISTS orders (
  id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  name                   VARCHAR      NOT NULL
);