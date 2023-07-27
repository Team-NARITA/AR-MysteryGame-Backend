package com.teamnarita.armysterygamebackend.repository.mystery

import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.queryForList
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class MysteryRepository(val jdbcTemplate: JdbcTemplate): IMysteryRepository {
    override fun getSolvedMystery(userId: String): HashSet<SolvedMystery> {
        val solvedMysteries = jdbcTemplate.queryForList<SolvedMystery>("SELECT * FROM solved_mystery WHERE user_id='$userId'")
        println(solvedMysteries)
        return solvedMysteries.toHashSet()
    }

    private class SolvedMysteryRowMapper: RowMapper<SolvedMystery> {
        override fun mapRow(rs: ResultSet, rowNum: Int): SolvedMystery {
            val id = rs.getLong("id")
            val userId = rs.getString("user_id")
            val mysteryId = rs.getString("mystery_id")
            val solvedAt = rs.getLong("solved_at")
            return SolvedMystery(id, userId, mysteryId, solvedAt)
        }
    }
}