package com.teamnarita.armysterygamebackend.repository.chapter

import com.teamnarita.armysterygamebackend.model.dto.ClearedChapter

interface IChapterRepository {
    fun getClearedChapter(userId: String): HashSet<ClearedChapter>
}