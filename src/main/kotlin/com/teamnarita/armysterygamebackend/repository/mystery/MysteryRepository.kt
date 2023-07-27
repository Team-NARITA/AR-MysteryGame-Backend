package com.teamnarita.armysterygamebackend.repository.mystery

import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class MysteryRepository(val jdbcTemplate: JdbcTemplate): IMysteryRepository {
    companion object {

    }

    override fun getSolvedMystery(userId: String): HashSet<SolvedMystery> {
        val solvedMysteries = jdbcTemplate.query("SELECT * FROM solved_mystery WHERE user_id='$userId'", SolvedMysteryRowMapper())
        return solvedMysteries.toHashSet()
    }

    override fun addSolvedMystery(userId: String, solvedMystery: SolvedMystery) {

        jdbcTemplate.update("INSERT INTO solved_mystery (id, user_id, mystery_id, solved_at) VALUES (NULL, ?, ?, ?)",
            userId, solvedMystery.mysteryId, solvedMystery.solvedAt)
    }

    private class SolvedMysteryRowMapper: RowMapper<SolvedMystery> {
        override fun mapRow(rs: ResultSet, rowNum: Int): SolvedMystery {
            val userId = rs.getString("user_id")
            val mysteryId = rs.getString("mystery_id")
            val solvedAt = rs.getLong("solved_at")
            return SolvedMystery(userId, mysteryId, solvedAt)
        }
    }
}