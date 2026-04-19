package com.jb.coupons.coupon.infrastructure

import org.springframework.data.repository.CrudRepository

interface CouponEntityRepository : CrudRepository<CouponEntity, Int> {
}