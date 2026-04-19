package com.jb.coupons.coupon.application

import com.jb.coupons.coupon.api.CreateCouponRequest
import com.jb.coupons.coupon.api.CreateCouponResponse
import org.springframework.stereotype.Service
import java.time.Clock

@Service
class CreateCouponService(
    private val clock: Clock
) {
    fun create(createCouponRequest: CreateCouponRequest): CreateCouponResponse {
        return CreateCouponResponse(
            createCouponRequest.code!!,
            clock.instant(),
            createCouponRequest.maxUsages,
            createCouponRequest.country
        )
    }
}