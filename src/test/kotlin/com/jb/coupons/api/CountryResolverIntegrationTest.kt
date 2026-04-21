package com.jb.coupons.api

import com.jb.coupons.IntegrationTest
import com.jb.coupons.common.ApiError
import com.jb.coupons.common.CountryResolutionException
import com.jb.coupons.common.ErrorMessage
import com.jb.coupons.common.ErrorType
import com.jb.coupons.coupon.api.RedeemCouponRequest
import com.jb.coupons.coupon.application.CountryResolver
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.test.context.bean.override.mockito.MockitoBean

class CountryResolverIntegrationTest : IntegrationTest() {

    @MockitoBean
    lateinit var countryResolver: CountryResolver

    @Test
    fun `should return asd found when coupon does not exists`() {
        whenever(countryResolver.resolveCountry(any()))
            .thenThrow(CountryResolutionException("Country resolver error"))

        simplePost(
            "/coupons/redeem",
            RedeemCouponRequest("user1", "code1"),
            HttpStatus.SERVICE_UNAVAILABLE,
            ApiError(
                HttpStatus.SERVICE_UNAVAILABLE,
                ErrorType.EXTERNAL_SERVICE_ERROR,
                ErrorMessage.SERVICE_UNAVAILABLE,
                "Country resolver error"
            )
        )
    }
}