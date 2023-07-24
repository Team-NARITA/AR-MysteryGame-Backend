package com.teamnarita.armysterygamebackend.repository.user

import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.UserDTO

interface IUserRepository {
    fun find(userId: String): UserDTO?
    fun save(gameUser: GameUser): Boolean
    fun exists(userId: String): Boolean
}