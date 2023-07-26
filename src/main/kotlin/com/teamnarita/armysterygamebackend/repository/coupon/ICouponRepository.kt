package com.teamnarita.armysterygamebackend.repository.coupon

interface ICouponRepository {
    fun get(userId: String): HashSet<String>
}