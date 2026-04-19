create table coupon_entity
(
    id   int generated always as identity primary key,
    code text,

    unique (code)
);
