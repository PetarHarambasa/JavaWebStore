CREATE TABLE IF NOT EXISTS web_shop_schema.category (
    id_category SERIAL PRIMARY KEY ,
    name varchar(255) not null ,
    front_image_base64 text not null
);

CREATE TABLE IF NOT EXISTS web_shop_schema.merch (
    id_merch SERIAL PRIMARY KEY ,
    description varchar (255) not null ,
    price numeric (10, 2) not null ,
    rating numeric (1,0) CHECK (rating >= 1 AND rating <= 5) not null,
    front_image_base64 text not null,
    category_id int not null REFERENCES web_shop_schema.category(id_category) not null
);

CREATE TABLE IF NOT EXISTS web_shop_schema.merch_images (
    id_merch_images SERIAL PRIMARY KEY ,
    image_base64 text not null,
    merch_id int not null REFERENCES web_shop_schema.merch(id_merch) not null
);