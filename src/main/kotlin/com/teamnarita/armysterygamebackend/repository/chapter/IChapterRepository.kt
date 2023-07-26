package com.teamnarita.armysterygamebackend.repository.chapter

interface IChapterRepository {
    fun get(userId: String): HashSet<String>
}