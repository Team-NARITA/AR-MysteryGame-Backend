package com.teamnarita.armysterygamebackend.service.chapter

import com.teamnarita.armysterygamebackend.model.ChapterData
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter

interface IChapterService {
    fun clearChapter(user: GameUser, chapterId: String): ClearedChapter
    fun getNextChapter(user: GameUser): ChapterData
    fun getCurrentChapter(user: GameUser): ChapterData
    fun getChapterById(chapterId: String): ChapterData?
}