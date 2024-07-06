create extension if not exists "uuid-ossp";

create table if not exists "artist"
(
    id          UUID unique        not null default uuid_generate_v1(),
    name        varchar(255) unique not null,
    biography   text not null,
    photo       text not null,
    primary key (id)
);

alter table "artist"
    owner to postgres;