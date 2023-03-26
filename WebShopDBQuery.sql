create table if not exists user_role(
 id_user_role SERIAL PRIMARY KEY,
 name varchar(255) not null
);

INSERT INTO user_role (name) VALUES ('ADMIN');
INSERT INTO user_role (name) VALUES ('CUSTOMER');

create table if not exists shop_user(
    id_shop_user SERIAL PRIMARY KEY,
    userName varchar(255) not null,
    email varchar(255) not null,
    password varchar(255)not null,
    user_role_id int not null REFERENCES user_role(id_user_role)
);
