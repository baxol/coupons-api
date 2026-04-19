package com.jb.coupons.coupon.application

import com.jb.coupons.coupon.api.CreateCouponRequest
import com.jb.coupons.coupon.api.toDomain
import com.jb.coupons.coupon.domain.Coupon
import com.jb.coupons.coupon.domain.CouponRepository
import org.springframework.stereotype.Service
import java.time.Clock

@Service
class CreateCouponService(
    private val clock: Clock,
    private val couponRepository: CouponRepository
) {
    fun create(createCouponRequest: CreateCouponRequest): Coupon {
        val coupon = createCouponRequest.toDomain(clock.instant())
        return couponRepository.save(coupon)
    }
}