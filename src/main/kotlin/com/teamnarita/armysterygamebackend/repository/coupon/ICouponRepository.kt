package com.teamnarita.armysterygamebackend.repository.coupon

import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon

interface ICouponRepository {
    fun getUsedCoupon(userId: String): HashSet<UsedCoupon>
}