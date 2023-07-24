package com.teamnarita.armysterygamebackend.repository

import com.teamnarita.armysterygamebackend.model.GameUser

interface IUserRepository {
    fun find(): GameUser?
    fun save(): Boolean
    fun existName(): Boolean
}