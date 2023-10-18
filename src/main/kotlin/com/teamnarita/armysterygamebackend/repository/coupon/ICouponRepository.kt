package com.teamnarita.armysterygamebackend.repository.coupon

import com.teamnarita.armysterygamebackend.model.CouponData
import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon

interface ICouponRepository {
    fun loadCouponMaster()
    fun getCouponList(): List<CouponData>
    fun getUsedCoupon(userId: String): HashSet<UsedCoupon>
    fun addUsedCoupon(usedCoupon: UsedCoupon)
}