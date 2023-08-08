package com.teamnarita.armysterygamebackend.repository.mystery

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.teamnarita.armysterygamebackend.model.MysteryData
import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery
import jakarta.annotation.PostConstruct
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.io.File
import java.nio.file.Paths
import java.sql.ResultSet

@Repository
class MysteryRepository(val jdbcTemplate: JdbcTemplate): IMysteryRepository {
    companion object {
        private val mysteryFolder = Paths.get("./mystery/")
        private val mysteryMaster = File(mysteryFolder.toFile(), "mysteryMaster.json")
        private val jsonMapper = ObjectMapper().registerKotlinModule()
    }

    private val mysteryDataMap: HashMap<String, MysteryData> = HashMap()

    @PostConstruct
    override fun loadMysteryMaster() {
        mysteryDataMap.clear()
        val mysteries = jsonMapper.readValue<List<MysteryData>>(mysteryMaster)
        for (mystery in mysteries) {
            mysteryDataMap[mystery.mysteryId] = mystery
        }
    }

    override fun getMysteryById(mysteryId: String): MysteryData? {
        return mysteryDataMap[mysteryId]
    }

    override fun getSolvedMystery(userId: String): HashSet<SolvedMystery> {
        val solvedMysteries = jdbcTemplate.query("SELECT * FROM solved_mystery WHERE user_id='$userId'", SolvedMysteryRowMapper())
        return solvedMysteries.toHashSet()
    }

    override fun addSolvedMystery(solvedMystery: SolvedMystery) {
        jdbcTemplate.update("INSERT INTO solved_mystery (id, user_id, mystery_id, solved_at) VALUES (NULL, ?, ?, ?)",
            solvedMystery.userId, solvedMystery.mysteryId, solvedMystery.solvedAt)
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