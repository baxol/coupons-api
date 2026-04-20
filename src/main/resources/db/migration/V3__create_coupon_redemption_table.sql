create table coupon_redemption_entity
(
    id         int generated always as identity primary key,
    coupon_id  int                      not null references coupon_entity (id) on delete cascade,
    user_ref   text                     not null,
    created_at timestamp with time zone not null,

    unique (coupon_id, user_ref)
);