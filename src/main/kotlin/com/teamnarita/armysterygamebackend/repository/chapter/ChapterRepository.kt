package com.teamnarita.armysterygamebackend.repository.chapter

import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import org.springframework.stereotype.Repository

@Repository
class ChapterRepository: IChapterRepository {
    override fun getClearedChapter(userId: String): HashSet<ClearedChapter> {
        TODO("Not yet implemented")
    }
}