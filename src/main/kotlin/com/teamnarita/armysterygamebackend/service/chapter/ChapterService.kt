package com.teamnarita.armysterygamebackend.service.chapter

import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import com.teamnarita.armysterygamebackend.repository.chapter.IChapterRepository
import com.teamnarita.armysterygamebackend.utility.TimeUtil
import org.springframework.stereotype.Service

@Service
class ChapterService(private val chapterRepository: IChapterRepository): IChapterService {
    private val chapterList: LinkedHashSet<ChapterData> = chapterRepository.loadChapterMaster()

    override fun clearChapter(user: GameUser, chapterId: String): ClearedChapter {
        TODO("Not yet implemented")
    }

    override fun getNextChapter(user: GameUser): ChapterData {
        TODO("Not yet implemented")
    }

    override fun getCurrentChapter(user: GameUser): ChapterData {
        for (chapter in chapterList) {
            if (user.isClearedChapter(chapter.chapterId)) continue
            return chapter
        }
        return chapterList.last()
    }

    override fun getChapterById(chapterId: String): ChapterData? {
        return chapterList.firstOrNull { it.chapterId == chapterId }
    }

    private fun getFirstChapter(): ChapterData {
        return chapterList.first()
    }
}