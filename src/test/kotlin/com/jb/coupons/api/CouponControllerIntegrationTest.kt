package com.jb.coupons.api

import com.jb.coupons.IntegrationTest
import com.jb.coupons.coupon.api.CreateCouponRequest
import com.jb.coupons.coupon.api.CreateCouponResponse
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class CouponControllerIntegrationTest : IntegrationTest() {
    
    @Test
    @Order(0)
    fun `create coupon`() {
        //given
        val request = CreateCouponRequest("TEST", 5, "PL")
        val response = CreateCouponResponse("TEST", clock.instant(), 5, "PL")

        //expect
        simplePostCompareJson(
            "/coupons/create",
            request,
            HttpStatus.CREATED,
            response
        )
    }

}