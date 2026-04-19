package com.jb.coupons.coupon.infrastructure

import org.springframework.data.annotation.Id

data class CouponEntity(
    @Id
    val id: Int?,
    val code: String,
)