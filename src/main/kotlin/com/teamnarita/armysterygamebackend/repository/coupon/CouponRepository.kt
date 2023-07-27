package com.teamnarita.armysterygamebackend.repository.coupon

import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class CouponRepository: ICouponRepository {
    override fun getUsedCoupon(userId: String): HashSet<UsedCoupon> {
        TODO("Not yet implemented")
    }

    private class UsedCouponRowMapper: RowMapper<UsedCoupon> {
        override fun mapRow(rs: ResultSet, rowNum: Int): UsedCoupon {
            val id = rs.getLong("id")
            val userId = rs.getString("user_id")
            val couponId = rs.getString("mysteryId")
            val usedAt = rs.getLong("solved_at")
            return UsedCoupon(id, userId, couponId, usedAt)
        }
    }
}