package com.teamnarita.armysterygamebackend.service.chapter

import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import org.springframework.stereotype.Service

@Service
class ChapterService: IChapterService {
    override fun clearChapter(user: GameUser, chapterId: String): ClearedChapter {
        TODO("Not yet implemented")
    }

    override fun getNextChapter(user: GameUser): ChapterData {
        TODO("Not yet implemented")
    }

    override fun getCurrentChapter(user: GameUser): ChapterData {
        TODO("Not yet implemented")
    }

    override fun getChapterById(chapterId: String): ChapterData {
        TODO("Not yet implemented")
    }
}