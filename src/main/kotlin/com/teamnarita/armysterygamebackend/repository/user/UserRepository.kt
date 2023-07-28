package com.teamnarita.armysterygamebackend.repository.user

import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.UserDTO
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class UserRepository(val jdbcTemplate: JdbcTemplate): IUserRepository {
    override fun find(userId: String): UserDTO? {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM game_user WHERE user_id='$userId'", UserRowMapper())
        } catch (e: IncorrectResultSizeDataAccessException) {
            e.printStackTrace()
            return null
        }
    }

    override fun save(gameUser: GameUser): Boolean {
        val sql = "REPLACE INTO game_user (user_id, user_name, create_at, role_id) VALUES ( ?, ?, ?, ?)"

        jdbcTemplate.update(sql, gameUser.userId, gameUser.userName, gameUser.createAt, gameUser.role.roleId)
        return true
    }

    override fun exists(userId: String): Boolean {
        return jdbcTemplate.query("SELECT * FROM game_user WHERE user_id='$userId'", UserRowMapper()).size >= 1
    }

    private class UserRowMapper: RowMapper<UserDTO> {
        override fun mapRow(rs: ResultSet, rowNum: Int): UserDTO {
            val userId = rs.getString("user_id")
            val userName = rs.getString("user_name")
            val createAt = rs.getLong("create_at")
            val roleId = rs.getInt("role_id")
            return UserDTO(userId, userName, createAt, roleId)
        }
    }
}