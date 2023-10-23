package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery
import com.teamnarita.armysterygamebackend.service.mystery.IMysteryService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/mystery")
class MysteryController(private val mysteryService: IMysteryService) {
    companion object {
        private val Logger = LoggerFactory.getLogger(MysteryController::class.java)
    }

    @PostMapping("/submit/{mysteryId}")
    fun submitMystery(@AuthenticationPrincipal principal: UserDetailsImpl,
                      @PathVariable mysteryId: String, @RequestBody requestBody: SubmitMystery
    ): ResponseEntity<MysteryResponse> {
        Logger.info("Request:POST /mystery/submit/${mysteryId} User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        if (mysteryService.checkAnswer(mysteryId,requestBody.answer)) {
            val gameUser = principal.gameUser
            val solvedMystery = gameUser.solvedMystery.firstOrNull { it.mysteryId == mysteryId} ?:
                mysteryService.solveMystery(gameUser, mysteryId)
            return ResponseEntity(MysteryResponse(true, solvedMystery), HttpStatus.OK)
        }
        return ResponseEntity(MysteryResponse(false, null), HttpStatus.OK)
    }

    data class SubmitMystery(val answer: String)
    data class MysteryResponse(val isCorrect: Boolean, val content: SolvedMystery?)
}