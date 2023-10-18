package com.teamnarita.armysterygamebackend.service.coupon

import com.teamnarita.armysterygamebackend.exception.coupon.CouponAlreadyUsedException
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
        val usedList = couponRepository.getUsedCoupon(user.userId)
        val result = mutableListOf<UserCouponData>()
        for (coupon in couponList) {
            val isUsed = usedList.any { it.couponId == coupon.couponId }
            result.add(UserCouponData(coupon, isUsed))
        }
        return result.toList()
    }

    override fun useCoupon(user: GameUser, couponId: String): UsedCoupon {
        val usedCouponList = couponRepository.getUsedCoupon(user.userId)
        if (usedCouponList.any {it.couponId == couponId}) throw CouponAlreadyUsedException(user.userId, "既に使用されています")
        val usedCoupon = UsedCoupon(user.userId, couponId, timeUtil.getCurrentTimeStamp())
        couponRepository.addUsedCoupon(usedCoupon)
        return usedCoupon
    }

    private fun isAvailable(user: GameUser, couponData: CouponData): Boolean {
        TODO()
    }
}