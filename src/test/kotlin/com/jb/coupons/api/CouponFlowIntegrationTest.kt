package com.jb.coupons.api

import com.jb.coupons.IntegrationTest
import com.jb.coupons.common.ApiError
import com.jb.coupons.common.ErrorMessage
import com.jb.coupons.common.ErrorType
import com.jb.coupons.coupon.api.CreateCouponRequest
import com.jb.coupons.coupon.api.CreateCouponResponse
import com.jb.coupons.coupon.api.RedeemCouponRequest
import com.jb.coupons.coupon.api.RedeemCouponResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import kotlin.test.assertContains
import kotlin.test.assertEquals

class CouponFlowIntegrationTest : IntegrationTest() {

    @Test
    fun `should return conflict when coupon already exists`() {
        simplePost(
            "/coupons/create",
            CreateCouponRequest("couponOne", 1, "PL"),
            HttpStatus.CREATED,
            CreateCouponResponse("couponOne", clock.instant(), 1, "PL")
        )

        simplePost(
            "/coupons/create",
            CreateCouponRequest("couponONE", 2, "DE"),
            HttpStatus.CONFLICT,
            ApiError(HttpStatus.CONFLICT, ErrorType.INCONSISTENT_DATA, ErrorMessage.COUPON_ALREADY_EXISTS)
        )

        runBlocking {
            val result = listOf(
                async {
                    simplePostResultStatus(
                        "/coupons/create",
                        CreateCouponRequest("couponTWO", 2, "DE")
                    )
                },
                async {
                    simplePostResultStatus(
                        "/coupons/create",
                        CreateCouponRequest("couponTwo", 2, "DE")
                    )
                }
            ).awaitAll()
            assertContains(result, HttpStatus.CREATED.value())
            assertContains(result, HttpStatus.CONFLICT.value())
        }
    }

    @Test
    fun `should create coupon and use it until limit reached`() {
        simplePost(
            "/coupons/create",
            CreateCouponRequest("coupon1", 4, "PL"),
            HttpStatus.CREATED,
            CreateCouponResponse("coupon1", clock.instant(), 4, "PL")
        )

        simplePost(
            "/coupons/redeem",
            RedeemCouponRequest("user1", "coupon1"),
            HttpStatus.OK,
            RedeemCouponResponse("coupon1")
        )

        runBlocking {
            val result = listOf(
                async {
                    simplePostResultStatus(
                        "/coupons/redeem",
                        RedeemCouponRequest("user2", "coupon1"),
                    )
                },
                async {
                    simplePostResultStatus(
                        "/coupons/redeem",
                        RedeemCouponRequest("user3", "coupon1"),
                    )
                }
            ).awaitAll()
            assertEquals(result.toSet().size, 1)
            assertEquals(result.first(), HttpStatus.OK.value())
        }

        runBlocking {
            val result = listOf(
                async {
                    simplePostResultStatus(
                        "/coupons/redeem",
                        RedeemCouponRequest("user4", "coupon1"),
                    )
                },
                async {
                    simplePostResultStatus(
                        "/coupons/redeem",
                        RedeemCouponRequest("user5", "coupon1"),
                    )
                }
            ).awaitAll()
            assertContains(result, HttpStatus.OK.value())
            assertContains(result, HttpStatus.CONFLICT.value())
        }


        simplePost(
            "/coupons/redeem",
            RedeemCouponRequest("user6", "coupon1"),
            HttpStatus.CONFLICT,
            ApiError(HttpStatus.CONFLICT,  ErrorType.INCONSISTENT_DATA, ErrorMessage.COUPON_LIMIT_REACHED)
        )
    }

    @Test
    fun `should create coupon and return conflict when used twice`() {
        simplePost(
            "/coupons/create",
            CreateCouponRequest("coupon2", 4, "PL"),
            HttpStatus.CREATED,
            CreateCouponResponse("coupon2", clock.instant(), 4, "PL")
        )

        simplePost(
            "/coupons/redeem",
            RedeemCouponRequest("user1", "coupon2"),
            HttpStatus.OK,
            RedeemCouponResponse("coupon2")
        )

        simplePost(
            "/coupons/redeem",
            RedeemCouponRequest("user1", "coupon2"),
            HttpStatus.CONFLICT,
            ApiError(
                HttpStatus.CONFLICT,
                ErrorType.INCONSISTENT_DATA,
                ErrorMessage.COUPON_ALREADY_USED
            )
        )
    }

    @Test
    fun `should return not found when coupon does not exists`() {
        simplePost(
            "/coupons/redeem",
            RedeemCouponRequest("user1", "nonExistingCoupon"),
            HttpStatus.NOT_FOUND,
            ApiError(
                HttpStatus.NOT_FOUND,
                ErrorType.RESOURCE_NOT_FOUND,
                ErrorMessage.COUPON_NOT_FOUND
            )
        )
    }

}