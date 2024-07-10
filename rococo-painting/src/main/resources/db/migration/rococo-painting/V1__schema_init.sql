create extension if not exists "uuid-ossp";

create table if not exists "painting"
(
    id          UUID unique        not null default uuid_generate_v1(),
    description text               not null,
    content     bytea              not null,
    artist_id   UUID               not null,
    museum_id   UUID,
    primary key (id)
);

alter table "painting"
    owner to postgres;