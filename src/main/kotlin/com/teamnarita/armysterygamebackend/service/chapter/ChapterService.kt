package com.teamnarita.armysterygamebackend.service.chapter

import com.teamnarita.armysterygamebackend.exception.chapter.ChapterNotFoundException
import com.teamnarita.armysterygamebackend.exception.UnauthorizedAccessException
import com.teamnarita.armysterygamebackend.exception.chapter.ClearJudgmentException
import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import com.teamnarita.armysterygamebackend.repository.chapter.IChapterRepository
import com.teamnarita.armysterygamebackend.repository.user.IUserRepository
import com.teamnarita.armysterygamebackend.utility.TimeUtil
import org.springframework.stereotype.Service
import java.io.File
import kotlin.jvm.Throws

@Service
class ChapterService(private val chapterRepository: IChapterRepository, private val userRepository: IUserRepository): IChapterService {

    @Throws(ClearJudgmentException::class, ChapterNotFoundException::class)
    override fun clearChapter(user: GameUser, chapterId: String): ClearedChapter {
        val chapter = getChapterById(chapterId)
        if (user.isClearedChapter(chapterId))
            throw ClearJudgmentException(user.userId, "このチャプターは既にクリア済みです")
        if (!chapter.belongMysteries.all { user.isSolvedMystery(it) })
            throw ClearJudgmentException(user.userId, "必要な謎を解いていません")

        val clearedChapter = ClearedChapter(user.userId, chapterId, TimeUtil.getCurrentTimeStamp())
        chapterRepository.addClearedChapter(clearedChapter)
        user.addClearedChapter(clearedChapter)
        user.currentChapter = getCurrentChapter(user)
        userRepository.save(user)
        return clearedChapter
    }

    override fun getCurrentChapter(user: GameUser): ChapterData {
        val chapterList = chapterRepository.getChapterList()
        for (chapter in chapterList) {
            if (user.isClearedChapter(chapter.chapterId)) continue
            return chapter
        }
        return chapterList.last()
    }

    @Throws(ChapterNotFoundException::class)
    override fun getChapterById(chapterId: String): ChapterData {
        return chapterRepository.getChapterList().firstOrNull { it.chapterId == chapterId }
            ?: throw ChapterNotFoundException(chapterId ,"ChapterId: $chapterId が見つかりません")
    }

    @Throws(UnauthorizedAccessException::class)
    override fun getChapterDataByUser(user: GameUser, chapterId: String): ChapterData {
        if (authorizeAccess(user, chapterId)) {
            return getChapterById(chapterId)
        }
        throw UnauthorizedAccessException(user.userId, "チャプターにアクセス権限がありません")
    }

    @Throws(UnauthorizedAccessException::class)
    override fun getChapterFileByUser(user: GameUser, chapterId: String): File {
        if (authorizeAccess(user, chapterId)) {
            return chapterRepository.getChapterFile(chapterId)
        }
        throw UnauthorizedAccessException(user.userId, "チャプターにアクセス権限がありません")
    }

    override fun getAuthorizedChapterByUser(user: GameUser): List<ChapterData> {
        val chapters = mutableListOf<ChapterData>()
        for (clearedChapter in user.clearedChapter) {
            chapters.add(getChapterDataByUser(user, clearedChapter.chapterId))
        }
        if (!chapters.any { it.chapterId == user.currentChapter.chapterId }) {
            chapters.add(getCurrentChapter(user))
        }
        return chapters.toList()
    }

    private fun authorizeAccess(user: GameUser, chapterId: String): Boolean {
        if (user.isClearedChapter(chapterId)) return true
        return getCurrentChapter(user).chapterId == chapterId
    }
}