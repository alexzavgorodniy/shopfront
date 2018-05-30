CREATE USER "shop-user"
  WITH
    LOGIN
    NOSUPERUSER
    NOINHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION
    PASSWORD 'shop-user';

CREATE DATABASE "shopfront-db"
  WITH
    OWNER = "shop-user"
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default;

GRANT ALL ON DATABASE "shopfront-db" TO "shop-user";

GRANT TEMPORARY, CONNECT ON DATABASE "shopfront-db" TO PUBLIC;