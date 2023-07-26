package com.teamnarita.armysterygamebackend.repository.mystery

interface IMysteryRepository {
    fun get(userId: String): HashSet<String>
}