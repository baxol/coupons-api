package com.jb.coupons.coupon.infrastructure

import org.springframework.data.repository.CrudRepository

interface JdbcCouponRepository : CrudRepository<CouponEntity, Int> {
}