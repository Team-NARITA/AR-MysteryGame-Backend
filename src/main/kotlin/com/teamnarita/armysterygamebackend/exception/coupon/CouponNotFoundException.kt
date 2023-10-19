package com.teamnarita.armysterygamebackend.exception.coupon

class CouponNotFoundException(val couponId: String, msg: String): Exception(msg) {
}