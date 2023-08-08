package com.teamnarita.armysterygamebackend.service.mystery

import com.teamnarita.armysterygamebackend.exception.UnauthorizedAccessException
import com.teamnarita.armysterygamebackend.exception.mystery.MysteryNotFoundException
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery
import com.teamnarita.armysterygamebackend.repository.mystery.IMysteryRepository
import com.teamnarita.armysterygamebackend.utility.TimeUtil
import org.springframework.stereotype.Service

@Service
class MysteryService(private val mysteryRepository: IMysteryRepository): IMysteryService {
    private val timeUtil = TimeUtil

    override fun solveMystery(user: GameUser, mysteryId: String): SolvedMystery {
        if (!user.currentChapter.belongMysteries.contains(mysteryId))
            throw UnauthorizedAccessException(user.userId, "現在のチャプターに存在しない謎です")
        val solvedMystery = SolvedMystery(user.userId, mysteryId, timeUtil.getCurrentTimeStamp())
        mysteryRepository.addSolvedMystery(solvedMystery)
        user.addSolvedMystery(solvedMystery)
        return solvedMystery
    }

    override fun checkAnswer(mysteryId: String, answer: String): Boolean {
        val mystery = mysteryRepository.getMysteryById(mysteryId)
            ?: throw MysteryNotFoundException(mysteryId, "$mysteryId が見つかりませんでした")
        return mystery.answers.contains(answer)
    }
}