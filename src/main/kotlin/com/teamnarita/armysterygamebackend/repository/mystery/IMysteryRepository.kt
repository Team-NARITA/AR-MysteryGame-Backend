package com.teamnarita.armysterygamebackend.repository.mystery

import com.teamnarita.armysterygamebackend.model.GameUser

interface IMysteryRepository {
    fun get(userId: String): HashSet<String>
    fun save(gameUser: GameUser): Boolean
}