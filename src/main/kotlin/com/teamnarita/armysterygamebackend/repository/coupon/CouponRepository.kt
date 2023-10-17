package com.teamnarita.armysterygamebackend.repository.coupon

import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class CouponRepository(private val jdbcTemplate: JdbcTemplate): ICouponRepository {
    override fun getUsedCoupon(userId: String): HashSet<UsedCoupon> {
        val usedCoupon = jdbcTemplate.query("SELECT * FROM used_coupon WHERE user_id='?'", UsedCouponRowMapper(), userId)
        return usedCoupon.toHashSet()
    }

    private class UsedCouponRowMapper: RowMapper<UsedCoupon> {
        override fun mapRow(rs: ResultSet, rowNum: Int): UsedCoupon {
            val userId = rs.getString("user_id")
            val couponId = rs.getString("mysteryId")
            val usedAt = rs.getLong("solved_at")
            return UsedCoupon(userId, couponId, usedAt)
        }
    }
}