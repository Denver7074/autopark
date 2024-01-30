create table if not exists seller
(
    id               bigserial primary key,
    creat_date       timestamp   not null,
    last_mod_date    timestamp   not null,
    name_company     varchar(30) not null,
    full_name_worker varchar(20) not null,
    email_company    varchar(50) not null,
    archived         boolean     not null
);

create table if not exists owner
(
    id              bigserial primary key,
    creat_date      timestamp   not null,
    last_mod_date   timestamp   not null,
    full_name_owner varchar(50) not null,
    number_phone    varchar(20) not null,
    email           varchar(50) not null,
    archived        boolean     not null
);

create table if not exists car
(
    id            bigserial primary key,
    creat_date    timestamp   not null,
    last_mod_date timestamp   not null,
    vin           varchar(50) not null,
    build_date    date        not null,
    seller_id     bigint references seller,
    owner_id      bigint references owner,
    archived      boolean     not null
);
