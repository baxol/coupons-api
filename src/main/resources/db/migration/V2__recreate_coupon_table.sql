drop table coupon_entity;

create extension if not exists citext;

create table coupon_entity
(
    id           int generated always as identity primary key,
    code         citext                   not null,
    created_at   timestamp with time zone not null,
    max_usages   int                      not null,
    actual_usage int                      not null,
    country      text                     not null,

    unique (code)
);