package com.teamnarita.armysterygamebackend.service.mystery

import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery

interface IMysteryService {
    fun solveMystery(user: GameUser, mysteryId: String): SolvedMystery

    fun checkAnswer(mysteryId: String, answer: String): Boolean
}