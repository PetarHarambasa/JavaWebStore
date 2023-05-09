CREATE TABLE IF NOT EXISTS web_shop_schema.login_history(
    id_login_history SERIAL PRIMARY KEY ,
    login_username varchar(255) not null ,
    login_email varchar(255) not null ,
    date_of_login TIMESTAMP DEFAULT LOCALTIMESTAMP,
    login_address varchar(255)
);