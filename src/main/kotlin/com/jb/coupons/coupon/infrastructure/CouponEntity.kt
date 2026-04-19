package com.jb.coupons.coupon.infrastructure

import com.jb.coupons.coupon.domain.Coupon
import org.springframework.data.annotation.Id

data class CouponEntity(
    @Id
    val id: Int?,
    val code: String,
)

fun Coupon.toEntity(id: Int? = null) = CouponEntity(
    id = id,
    code = code,
)