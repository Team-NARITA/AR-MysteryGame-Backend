package com.teamnarita.armysterygamebackend.repository.chapter

import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter

interface IChapterRepository {
    fun loadChapterMaster(): LinkedHashSet<ChapterData>
    fun getClearedChapter(userId: String): HashSet<ClearedChapter>
    fun addClearedChapter(clearedChapter: ClearedChapter)
}