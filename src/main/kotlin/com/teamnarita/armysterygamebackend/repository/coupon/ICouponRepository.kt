package com.teamnarita.armysterygamebackend.repository.coupon

import com.teamnarita.armysterygamebackend.model.GameUser

interface ICouponRepository {
    fun get(userId: String): HashSet<String>
    fun save(user: GameUser): Boolean
}