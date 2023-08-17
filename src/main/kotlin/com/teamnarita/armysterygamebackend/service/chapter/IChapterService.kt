package com.teamnarita.armysterygamebackend.service.chapter

import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import java.io.File

interface IChapterService {
    fun clearChapter(user: GameUser, chapterId: String): ClearedChapter
    fun getCurrentChapter(user: GameUser): ChapterData
    fun getChapterById(chapterId: String): ChapterData
    fun getChapterDataByUser(user: GameUser, chapterId: String): ChapterData
    fun getChapterFileByUser(user: GameUser, chapterId: String): File
    fun getAuthorizedChapterByUser(user: GameUser): List<ChapterData>
}