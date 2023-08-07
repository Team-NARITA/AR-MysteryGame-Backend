package com.teamnarita.armysterygamebackend.repository.mystery

import com.teamnarita.armysterygamebackend.model.MysteryData
import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery

interface IMysteryRepository {
    fun loadMysteryMaster()
    fun getMysteryById(mysteryId: String): MysteryData?
    fun getSolvedMystery(userId: String): HashSet<SolvedMystery>
    fun addSolvedMystery(solvedMystery: SolvedMystery)
}