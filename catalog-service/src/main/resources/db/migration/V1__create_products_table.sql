create sequence products_id_seq start with 1 increment by 50;

create table products(
    id bigint default nextval('products_id_seq') not null,
    code        text not null unique,
    name        text not null,
    description text,
    image_url   text,
    price       numeric(10,2) not null,
    primary key(id)
)