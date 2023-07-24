package com.teamnarita.armysterygamebackend.repository.coupon

import com.teamnarita.armysterygamebackend.model.GameUser
import org.springframework.stereotype.Repository

@Repository
class CouponRepository: ICouponRepository {
    override fun get(userId: String): HashSet<String> {
        TODO("Not yet implemented")
    }

    override fun save(user: GameUser): Boolean {
        TODO("Not yet implemented")
    }
}