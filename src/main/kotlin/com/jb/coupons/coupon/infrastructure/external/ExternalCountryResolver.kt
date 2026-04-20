package com.jb.coupons.coupon.infrastructure.external

import com.jb.coupons.coupon.application.CountryResolver
import org.springframework.stereotype.Service

@Service
class ExternalCountryResolver : CountryResolver {

    override fun resolve(ipAddress: String): String {
//        TODO
        return "PL"
    }

}