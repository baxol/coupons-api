package com.jb.coupons.coupon.application

import com.jb.coupons.coupon.api.CreateCouponRequest
import com.jb.coupons.coupon.api.RedeemCouponRequest
import com.jb.coupons.coupon.api.toDomain
import com.jb.coupons.coupon.domain.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock

@Service
class CouponService(
    private val clock: Clock,
    private val couponRepository: CouponRepository,
    private val couponRedemptionRepository: CouponRedemptionRepository
) {
    @Transactional
    fun create(request: CreateCouponRequest): Coupon {
        val coupon = request.toDomain(clock.instant())

        if (couponRepository.existsByCodeIgnoreCase(coupon.code))
            throw CouponAlreadyExistsException()

        return couponRepository.save(coupon)
    }

    @Transactional
    fun redeem(request: RedeemCouponRequest, country: String): CouponRedemption {
        val couponRedemption = request.toDomain(clock.instant())
        val coupon = couponRepository.findByCodeIgnoreCase(couponRedemption.code)
            ?: throw CouponNotFoundException()

        coupon.canBeUsed(Country.create(country))
            .onFailure { error ->
                when (error) {
                    is CouponError.InvalidCountry -> throw InvalidCouponCountryException()
                }
            }

        if (couponRedemptionRepository.existsByUserIdAndCouponCode(couponRedemption.userId, coupon.code))
            throw CouponAlreadyUsedException()

        if (!couponRepository.incrementUsageIfAvailable(coupon.code))
            throw CouponLimitReachedException()

        return couponRedemptionRepository.save(couponRedemption)
    }
}