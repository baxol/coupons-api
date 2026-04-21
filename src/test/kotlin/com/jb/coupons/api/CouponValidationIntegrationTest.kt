package com.jb.coupons.api

import com.jb.coupons.IntegrationTest
import com.jb.coupons.common.ApiError
import com.jb.coupons.common.ErrorType
import com.jb.coupons.coupon.api.CreateCouponRequest
import com.jb.coupons.coupon.api.RedeemCouponRequest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class CouponValidationIntegrationTest : IntegrationTest() {

    @Test
    fun `should return bad request for redeem with blank user id and code`() {
        simplePost(
            "/coupons/redeem",
            RedeemCouponRequest(" ", " "),
            HttpStatus.BAD_REQUEST,
            ApiError(
                HttpStatus.BAD_REQUEST,
                ErrorType.BAD_REQUEST,
                null,
                "code - must not be blank,userId - must not be blank"
            )
        )

        simplePost(
            "/coupons/redeem",
            RedeemCouponRequest("user", " "),
            HttpStatus.BAD_REQUEST,
            ApiError(
                HttpStatus.BAD_REQUEST,
                ErrorType.BAD_REQUEST,
                null,
                "code - must not be blank"
            )
        )
    }

    @Test
    fun `should return bad request for create coupon with blank code`() {
        simplePost(
            "/coupons/create",
            CreateCouponRequest(" ", 4, "PL"),
            HttpStatus.BAD_REQUEST,
            ApiError(
                HttpStatus.BAD_REQUEST,
                ErrorType.BAD_REQUEST,
                null,
                "code - must not be blank"
            )
        )
    }

    @Test
    fun `should return bad request for create coupon with zero max usages`() {
        simplePost(
            "/coupons/create",
            CreateCouponRequest("000", 0, "PL"),
            HttpStatus.BAD_REQUEST,
            ApiError(
                HttpStatus.BAD_REQUEST,
                ErrorType.BAD_REQUEST,
                null,
                "maxUsages - must be greater than or equal to 1"
            )
        )
    }

    @Test
    fun `should return bad request for create coupon with wrong country`() {
        simplePost(
            "/coupons/create",
            CreateCouponRequest("000", 4, ""),
            HttpStatus.BAD_REQUEST,
            ApiError(
                HttpStatus.BAD_REQUEST,
                ErrorType.BAD_REQUEST,
                null,
                "country - must not be blank,country - size must be between 2 and 2"
            )
        )

        simplePost(
            "/coupons/create",
            CreateCouponRequest("000", 4, "POLSKA"),
            HttpStatus.BAD_REQUEST,
            ApiError(
                HttpStatus.BAD_REQUEST,
                ErrorType.BAD_REQUEST,
                null,
                "country - size must be between 2 and 2"
            )
        )
    }

}