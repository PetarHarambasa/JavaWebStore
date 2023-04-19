ALTER table web_shop_schema.category
    ALTER column front_image_base64 set data type text;

ALTER table web_shop_schema.merch
    ALTER column price set data type numeric (10, 2),
    ALTER column rating set data type numeric (1,0),
    ALTER column front_image_base64 set data type text;

ALTER table web_shop_schema.merch_images
    ALTER column image_base64 set data type text;