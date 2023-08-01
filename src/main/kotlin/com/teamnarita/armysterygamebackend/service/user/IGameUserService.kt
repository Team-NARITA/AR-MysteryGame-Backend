package com.teamnarita.armysterygamebackend.service.user

import com.teamnarita.armysterygamebackend.exception.user.UserAlreadyExistException
import com.teamnarita.armysterygamebackend.exception.user.UserNotFoundException
import com.teamnarita.armysterygamebackend.model.GameUser
import kotlin.jvm.Throws

interface IGameUserService {

    @Throws(UserAlreadyExistException::class)
    fun register(userId: String, userName: String): GameUser

    fun userExist(userId: String): Boolean

    @Throws(UserNotFoundException::class)
    fun getUser(userId: String): GameUser

    fun saveUser(user: GameUser)
}