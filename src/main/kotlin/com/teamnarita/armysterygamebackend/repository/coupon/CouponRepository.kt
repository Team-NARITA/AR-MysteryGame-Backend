package com.teamnarita.armysterygamebackend.repository.coupon

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.teamnarita.armysterygamebackend.exception.coupon.CouponNotFoundException
import com.teamnarita.armysterygamebackend.model.CouponData
import com.teamnarita.armysterygamebackend.model.dto.UsedCoupon
import jakarta.annotation.PostConstruct
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.io.File
import java.nio.file.Paths
import java.sql.ResultSet

@Repository
class CouponRepository(private val jdbcTemplate: JdbcTemplate): ICouponRepository {
    companion object {
        private val couponFolder = Paths.get("./coupon/")
        private val couponMaster = File(couponFolder.toFile(), "couponMaster.json")
        private val jsonMapper = ObjectMapper().registerKotlinModule()
    }

    private val couponList: HashMap<String, CouponData> = HashMap()

    @PostConstruct
    override fun loadCouponMaster() {
        couponList.clear()
        for (couponData in jsonMapper.readValue<List<CouponData>>(couponMaster)) {
            couponList[couponData.couponId] = couponData
        }
    }

    override fun getCouponList(): List<CouponData> {
        return couponList.values.toList()
    }

    override fun getCouponById(couponId: String): CouponData {
        if (!couponList.containsKey(couponId)) throw CouponNotFoundException(couponId, "couponId: $couponId が見つかりません")
        return couponList[couponId]!!
    }

    override fun getUsedCoupon(userId: String): HashSet<UsedCoupon> {
        val usedCoupon = jdbcTemplate.query("SELECT * FROM used_coupon WHERE user_id=?", UsedCouponRowMapper(), userId)
        return usedCoupon.toHashSet()
    }

    override fun addUsedCoupon(usedCoupon: UsedCoupon) {
        val sql = "INSERT INTO used_coupon(user_id, coupon_id, used_at) VALUES (?, ?, ?)"
        jdbcTemplate.update(sql, usedCoupon.userId, usedCoupon.couponId, usedCoupon.usedAt)
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