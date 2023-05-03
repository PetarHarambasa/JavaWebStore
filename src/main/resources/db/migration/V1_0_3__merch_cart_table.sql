CREATE TABLE IF NOT EXISTS web_shop_schema.merch_cart (
    id_merch SERIAL PRIMARY KEY ,
    amount int not null ,
    merch_id int not null REFERENCES web_shop_schema.merch(id_merch) not null ,
    shop_user_id int not null REFERENCES web_shop_schema.shop_user(id_shop_user) not null
);