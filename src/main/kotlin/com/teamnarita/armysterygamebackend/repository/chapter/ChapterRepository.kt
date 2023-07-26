package com.teamnarita.armysterygamebackend.repository.chapter

import org.springframework.stereotype.Repository

@Repository
class ChapterRepository: IChapterRepository {
    override fun get(userId: String): HashSet<String> {
        TODO("Not yet implemented")
    }
}