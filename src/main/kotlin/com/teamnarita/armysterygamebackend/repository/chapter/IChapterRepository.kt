package com.teamnarita.armysterygamebackend.repository.chapter

import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter
import java.io.File

interface IChapterRepository {
    fun loadChapterMaster()
    fun getChapterList(): LinkedHashSet<ChapterData>
    fun getChapterFile(chapterId: String): File
    fun getClearedChapter(userId: String): HashSet<ClearedChapter>
    fun addClearedChapter(clearedChapter: ClearedChapter)
}