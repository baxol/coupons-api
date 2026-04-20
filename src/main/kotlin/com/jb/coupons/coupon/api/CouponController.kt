package com.jb.coupons.coupon.api

import com.jb.coupons.coupon.application.CouponApplicationService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("coupons")
class CouponController(
    private val couponApplicationService: CouponApplicationService
) {

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody createCouponRequest: CreateCouponRequest
    ): CreateCouponResponse =
        couponApplicationService.create(createCouponRequest)

    @PostMapping("redeem")
    fun redeem(
        @RequestBody redeemCouponRequest: RedeemCouponRequest,
        httpRequest: HttpServletRequest
    ): RedeemCouponResponse =
        couponApplicationService.redeem(redeemCouponRequest, httpRequest.remoteAddr)

}