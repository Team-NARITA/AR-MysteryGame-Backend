package com.teamnarita.armysterygamebackend.exception.coupon

class CouponAlreadyUsedException(val userId: String, msg: String): Exception() {
}