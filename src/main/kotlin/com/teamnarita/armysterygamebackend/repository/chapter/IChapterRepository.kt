package com.teamnarita.armysterygamebackend.repository.chapter

import com.teamnarita.armysterygamebackend.model.GameUser

interface IChapterRepository {
    fun get(userId: String): HashSet<String>
    fun save(gameUser: GameUser): Boolean
}