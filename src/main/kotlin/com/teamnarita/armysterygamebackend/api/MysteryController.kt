package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.model.dto.SolvedMystery
import com.teamnarita.armysterygamebackend.service.mystery.IMysteryService
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

    @PostMapping("/submit/{mysteryId}")
    fun submitMystery(@AuthenticationPrincipal principal: UserDetailsImpl,
                      @PathVariable mysteryId: String, @RequestBody requestBody: SubmitMystery
    ): ResponseEntity<MysteryResponse> {
        if (mysteryService.checkAnswer(mysteryId,requestBody.answer)) {
            val solvedMystery = mysteryService.solveMystery(principal.gameUser, mysteryId)
            return ResponseEntity(MysteryResponse(true, solvedMystery), HttpStatus.OK)
        }
        return ResponseEntity(MysteryResponse(false, null), HttpStatus.OK)
    }

    data class SubmitMystery(val answer: String)
    data class MysteryResponse(val isCollect: Boolean, val content: SolvedMystery?)
}