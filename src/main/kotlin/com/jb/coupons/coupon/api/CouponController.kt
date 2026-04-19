package com.jb.coupons.coupon.api

import com.jb.coupons.coupon.application.CreateCouponService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("coupons")
class CouponController(
    private val createCouponService: CreateCouponService
) {

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody createCouponRequest: CreateCouponRequest
    ): CreateCouponResponse =
        createCouponService.create(createCouponRequest)
            .toDto()

    @PostMapping("redeem")
    fun redeem(
        @RequestBody redeemCouponRequest: RedeemCouponRequest
    ): RedeemCouponResponse {
        TODO()
    }

}