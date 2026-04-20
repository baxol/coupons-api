package com.jb.coupons.coupon.domain

import java.time.Instant

class CouponCode private constructor(
    val value: String,
) {
    companion object {
        fun create(code: String): CouponCode {
            val clean = code.trim()

            require(clean.isNotBlank()) {
                "Can not create blank code"
            }

            return CouponCode(clean)
        }
    }
}

class Coupon(
    val code: CouponCode,
    val createdAt: Instant,
    val maxUsages: Int,
    val actualUsage: Int,
    val country: String
) {

    companion object {
        fun create(code: String, createdAt: Instant, maxUsages: Int, country: String): Coupon {
            return Coupon(
                CouponCode.create(code),
                createdAt,
                maxUsages,
                0,
                country
            )
        }
    }

    fun canBeUsed(userCountry: String): Result<Unit> =
        when {
            country != userCountry ->
                Result.failure(CouponError.InvalidCountry())

            else -> Result.success(Unit)
        }
}

sealed class CouponError : Throwable() {
    class InvalidCountry: CouponError()
}