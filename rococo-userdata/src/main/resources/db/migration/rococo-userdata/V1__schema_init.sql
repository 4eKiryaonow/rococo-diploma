create extension if not exists "uuid-ossp";

create table if not exists "userdata"
(
    id          UUID unique        not null default uuid_generate_v1(),
    username    varchar(50)        not null,
    firstname   varchar(50),
    surname     varchar(50),
    photo       bytea,
    primary key (id)
);

alter table "userdata"
    owner to postgres;