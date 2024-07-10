create extension if not exists "uuid-ossp";

create table if not exists "museum"
(
    id          UUID unique        not null default uuid_generate_v1(),
    title       varchar(255)       unique not null,
    city        varchar(255)       not null,
    country     varchar(255)       not null,
    description text               not null,
    photo       bytea              not null,
    primary key (id)
);

alter table "museum"
    owner to postgres;