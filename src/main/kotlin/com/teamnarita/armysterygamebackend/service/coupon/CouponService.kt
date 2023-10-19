package com.teamnarita.armysterygamebackend.service.coupon

import com.teamnarita.armysterygamebackend.exception.coupon.CouponNotAvailableException
import com.teamnarita.armysterygamebackend.model.CouponData
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.UserCouponData
import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon
import com.teamnarita.armysterygamebackend.repository.coupon.ICouponRepository
import com.teamnarita.armysterygamebackend.utility.TimeUtil
import org.springframework.stereotype.Service

@Service
class CouponService(private val couponRepository: ICouponRepository): ICouponService {
    private val timeUtil = TimeUtil

    override fun getUserCouponList(user: GameUser): List<UserCouponData> {
        val couponList = couponRepository.getCouponList()
        val result = mutableListOf<UserCouponData>()
        for (coupon in couponList) {
            val isAvailable = isAvailable(user, coupon)
            result.add(UserCouponData(coupon, isAvailable))
        }
        return result.toList()
    }

    override fun useCoupon(user: GameUser, couponId: String): UsedCoupon {
        val couponData = couponRepository.getCouponById(couponId)
        if (isAvailable(user, couponData)) throw CouponNotAvailableException(user.userId, "このクーポンは使用不可能です")
        val usedCoupon = UsedCoupon(user.userId, couponId, timeUtil.getCurrentTimeStamp())
        couponRepository.addUsedCoupon(usedCoupon)
        user.addUsedCoupon(usedCoupon)
        return usedCoupon
    }

    private fun isAvailable(user: GameUser, couponData: CouponData): Boolean {
        if (user.usedCoupon.any { it.couponId == couponData.couponId}) return false
        return user.isClearedChapter(couponData.couponId)
    }
}