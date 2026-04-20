package com.jb.coupons.coupon.api

import com.jb.coupons.coupon.domain.Coupon
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.Instant

data class CreateCouponRequest(
    @field:NotBlank
    val code: String,

    @field:Min(1)
    val maxUsages: Int,

    @field:NotBlank
    @field:Size(min = 2, max = 2)
    val country: String
)


data class CreateCouponResponse(
    val code: String,
    val createdAt: Instant,
    val maxUsages: Int,
    val country: String
)

fun Coupon.toDto(): CreateCouponResponse =
    CreateCouponResponse(code.value, createdAt, maxUsages, country)

fun CreateCouponRequest.toDomain(createdAt: Instant): Coupon =
    Coupon.create(code, createdAt, maxUsages, country)