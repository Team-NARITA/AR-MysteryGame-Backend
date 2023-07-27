package com.teamnarita.armysterygamebackend.repository.chapter

import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class ChapterRepository(private val jdbcTemplate: JdbcTemplate): IChapterRepository {
    override fun getClearedChapter(userId: String): HashSet<ClearedChapter> {
        val clearedChapter = jdbcTemplate.query("SELECT * FROM cleared_chapter WHERE user_id='$userId'", ClearedChapterRowMapper())
        return clearedChapter.toHashSet()
    }

    private class ClearedChapterRowMapper: RowMapper<ClearedChapter> {
        override fun mapRow(rs: ResultSet, rowNum: Int): ClearedChapter {
            val id = rs.getLong("id")
            val userId = rs.getString("user_id")
            val chapterId = rs.getString("chapter_id")
            val clearedAt = rs.getLong("cleared_at")
            return ClearedChapter(id, userId, chapterId, clearedAt)
        }
    }
}