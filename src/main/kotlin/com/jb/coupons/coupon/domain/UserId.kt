package com.jb.coupons.coupon.domain

class UserId(
    val value: String
) {
    init {
        require(value.isNotBlank()) { "User id can not be blank" }
    }

}