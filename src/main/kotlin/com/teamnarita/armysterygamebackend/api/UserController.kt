package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.exception.UserAlreadyExistException
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.service.IGameUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/users")
class UserController(private val gameUserService: IGameUserService) {
    @GetMapping("/hello")
    fun hello(@AuthenticationPrincipal principal: UserDetailsImpl): ResponseEntity<GameUser> {

        println(principal)

        return ResponseEntity(principal.gameUser, HttpStatus.OK)
    }

    @PostMapping("/register")
    fun registerUser(@AuthenticationPrincipal principal: UserDetailsImpl, @RequestBody requestBody: String): ResponseEntity<GameUser> {

        try {
            if (principal.gameUser.role == GameUser.UserRole.UNREGISTER_USER) {
                val gameUser = gameUserService.register(principal.gameUser.userId, "testUser")
                return ResponseEntity(gameUser, HttpStatus.OK)
            } else {
                return ResponseEntity(HttpStatus.CONFLICT)
            }
        } catch (e: UserAlreadyExistException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}