package com.teamnarita.armysterygamebackend.service.chapter

import com.teamnarita.armysterygamebackend.exception.chapter.ChapterNotFoundException
import com.teamnarita.armysterygamebackend.exception.chapter.ClearJudgmentException
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
        val chapter = getChapterById(chapterId)
        if (!chapter.belongMysteries.all { user.isSolvedMystery(it) })
            throw ClearJudgmentException(user.userId, "必要な謎を解いていません")

        val clearedChapter = ClearedChapter(user.userId, chapterId, TimeUtil.getCurrentTimeStamp())
        chapterRepository.addClearedChapter(clearedChapter)
        user.addClearedChapter(clearedChapter)
        return clearedChapter
    }

    override fun getCurrentChapter(user: GameUser): ChapterData {
        for (chapter in chapterList) {
            if (user.isClearedChapter(chapter.chapterId)) continue
            return chapter
        }
        return chapterList.last()
    }

    override fun getChapterById(chapterId: String): ChapterData {
        return chapterList.firstOrNull { it.chapterId == chapterId } ?: throw ChapterNotFoundException("ChapterId: $chapterId が見つかりません")
    }

    private fun getFirstChapter(): ChapterData {
        return chapterList.first()
    }
}