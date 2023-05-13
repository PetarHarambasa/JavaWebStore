CREATE TABLE IF NOT EXISTS web_shop_schema.purchased_type (
    id_purchase_type SERIAL PRIMARY KEY ,
    name varchar(255) not null
);

INSERT INTO web_shop_schema.purchased_type (name) VALUES ('PAYPAL');
INSERT INTO web_shop_schema.purchased_type (name) VALUES ('CASH');

CREATE TABLE IF NOT EXISTS web_shop_schema.purchased_bill (
    id_purchase_bill SERIAL PRIMARY KEY ,
    date_of_buying TIMESTAMP DEFAULT LOCALTIMESTAMP,
    total_price numeric (10, 2) not null ,
    purchase_type_id int not null REFERENCES web_shop_schema.purchased_type(id_purchase_type) not null
);

CREATE TABLE IF NOT EXISTS web_shop_schema.purchased_cart (
    id_purchased_cart SERIAL PRIMARY KEY ,
    amount int not null ,
    merch_id int not null REFERENCES web_shop_schema.merch(id_merch) not null ,
    shop_user_id int not null REFERENCES web_shop_schema.shop_user(id_shop_user) not null,
    purchased_bill_id int not null REFERENCES web_shop_schema.purchased_bill(id_purchase_bill) not null
);