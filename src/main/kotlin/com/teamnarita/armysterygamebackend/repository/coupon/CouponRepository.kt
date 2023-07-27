package com.teamnarita.armysterygamebackend.repository.coupon

import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon
import org.springframework.stereotype.Repository

@Repository
class CouponRepository: ICouponRepository {
    override fun getUsedCoupon(userId: String): HashSet<UsedCoupon> {
        TODO("Not yet implemented")
    }

}