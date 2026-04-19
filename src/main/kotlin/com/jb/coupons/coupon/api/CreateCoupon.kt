package com.jb.coupons.coupon.api

import com.jb.coupons.coupon.domain.Coupon
import java.time.Instant

data class CreateCouponRequest(
    val code: String,
    val maxUsages: Int,
    val country: String
)


data class CreateCouponResponse(
    val code: String,
    val createdAt: Instant,
    val maxUsages: Int,
    val country: String
)

fun CreateCouponRequest.toDomain(
    createdAt: Instant,
): Coupon =
     Coupon(code, createdAt, maxUsages, 0, country)


fun Coupon.toDto() =
    CreateCouponResponse(code, createdAt, maxUsages, country)