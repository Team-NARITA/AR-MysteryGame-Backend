package com.teamnarita.armysterygamebackend.repository.chapter

import com.teamnarita.armysterygamebackend.model.GameUser
import org.springframework.stereotype.Repository

@Repository
class ChapterRepository: IChapterRepository {
    override fun get(userId: String): HashSet<String> {
        TODO("Not yet implemented")
    }

    override fun save(gameUser: GameUser): Boolean {
        TODO("Not yet implemented")
    }
}