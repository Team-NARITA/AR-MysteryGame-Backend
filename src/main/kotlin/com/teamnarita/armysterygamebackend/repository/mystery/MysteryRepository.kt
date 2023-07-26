package com.teamnarita.armysterygamebackend.repository.mystery

import org.springframework.stereotype.Repository

@Repository
class MysteryRepository: IMysteryRepository {
    override fun get(userId: String): HashSet<String> {
        TODO("Not yet implemented")
    }
}