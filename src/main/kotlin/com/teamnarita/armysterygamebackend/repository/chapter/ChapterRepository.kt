package com.teamnarita.armysterygamebackend.repository.chapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import jakarta.annotation.PostConstruct
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Paths
import java.sql.ResultSet

@Repository
class ChapterRepository(private val jdbcTemplate: JdbcTemplate): IChapterRepository {
    companion object {
        private val chapterFolder = Paths.get("./chapter/")
        private val chapterMaster = File(chapterFolder.toFile(), "chapterMaster.json")
        private val jsonMapper = ObjectMapper().registerKotlinModule()
    }

    private val chapterList: LinkedHashSet<ChapterData> = LinkedHashSet()

    @PostConstruct
    override fun loadChapterMaster() {
        chapterList.clear()
        chapterList.addAll(jsonMapper.readValue(chapterMaster))
    }

    override fun getChapterList(): LinkedHashSet<ChapterData> {
        return chapterList
    }

    override fun getChapterFile(chapterId: String): File {
        val file = File(chapterFolder.toFile(), "$chapterId.json")
        if (!file.exists()) throw FileNotFoundException("$chapterId.json が見つかりませんでした")
        return file
    }

    override fun getClearedChapter(userId: String): MutableSet<ClearedChapter> {
        val clearedChapter = jdbcTemplate.query("SELECT * FROM cleared_chapter WHERE user_id=?", ClearedChapterRowMapper(), userId)
        return clearedChapter.toMutableSet()
    }

    override fun addClearedChapter(clearedChapter: ClearedChapter) {
        jdbcTemplate.update("INSERT INTO cleared_chapter (id, user_id, chapter_id, cleared_at) VALUES (NULL, ?, ?, ?)",
            clearedChapter.userId, clearedChapter.chapterId, clearedChapter.clearedAt)
    }

    private class ClearedChapterRowMapper: RowMapper<ClearedChapter> {
        override fun mapRow(rs: ResultSet, rowNum: Int): ClearedChapter {
            val userId = rs.getString("user_id")
            val chapterId = rs.getString("chapter_id")
            val clearedAt = rs.getLong("cleared_at")
            return ClearedChapter(userId, chapterId, clearedAt)
        }
    }
}