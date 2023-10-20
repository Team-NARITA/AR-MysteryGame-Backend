package com.teamnarita.armysterygamebackend.service.coupon

import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.UserCouponData
import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon

interface ICouponService {
    fun getCouponById(user: GameUser,couponId: String): UserCouponData
    fun useCoupon(user: GameUser, couponId: String): UsedCoupon
    fun getUserCouponList(user: GameUser): List<UserCouponData>
}