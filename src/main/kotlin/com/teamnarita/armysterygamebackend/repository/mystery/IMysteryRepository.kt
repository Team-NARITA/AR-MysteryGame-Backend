package com.teamnarita.armysterygamebackend.repository.mystery

import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery

interface IMysteryRepository {
    fun getSolvedMystery(userId: String): HashSet<SolvedMystery>
    fun addSolvedMystery(solvedMystery: SolvedMystery)
}