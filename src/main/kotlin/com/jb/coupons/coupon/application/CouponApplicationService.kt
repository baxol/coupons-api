package com.jb.coupons.coupon.application

import com.jb.coupons.coupon.api.*
import org.springframework.stereotype.Service

@Service
class CouponApplicationService(
    private val couponService: CouponService,
    private val countryResolver: CountryResolver,
) {

    fun create(request: CreateCouponRequest): CreateCouponResponse {
        return couponService.create(request)
            .toDto()
    }

    fun redeem(request: RedeemCouponRequest, ipAddress: String): RedeemCouponResponse {
        val country = countryResolver.resolve(ipAddress)
        return couponService.redeem(request, country)
            .toDto()
    }

}