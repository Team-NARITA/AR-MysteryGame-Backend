package com.teamnarita.armysterygamebackend.repository.coupon

import org.springframework.stereotype.Repository

@Repository
class CouponRepository: ICouponRepository {
    override fun get(userId: String): HashSet<String> {
        TODO("Not yet implemented")
    }

}