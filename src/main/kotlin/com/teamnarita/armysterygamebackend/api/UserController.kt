package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.exception.user.UserAlreadyExistException
import com.teamnarita.armysterygamebackend.exception.user.UserNotFoundException
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.service.user.IGameUserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/users")
class UserController(private val gameUserService: IGameUserService) {
    companion object {
        private val Logger = LoggerFactory.getLogger(UserController::class.java)
    }

    @PostMapping("/register")
    fun registerUser(@AuthenticationPrincipal principal: UserDetailsImpl, @RequestBody requestBody: RegisterUserBody): ResponseEntity<GameUser> {
        Logger.info("Request:Post /users/register User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val gameUser = gameUserService.register(principal.gameUser.userId, requestBody.username)
        return ResponseEntity(gameUser, HttpStatus.OK)
    }

    @GetMapping("/")
    fun getUser(@AuthenticationPrincipal principal: UserDetailsImpl, @RequestParam userId: String): ResponseEntity<GameUser> {
        Logger.info("Request:GET /users/ User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        val gameUser = gameUserService.getUser(userId)
        return ResponseEntity(gameUser, HttpStatus.OK)
    }

    @GetMapping("/self")
    fun getSelf(@AuthenticationPrincipal principal: UserDetailsImpl): ResponseEntity<GameUser> {
        Logger.info("Request:GET /users/self User: ${principal.gameUser.userName}[${principal.gameUser.userId}]")
        return ResponseEntity(principal.gameUser, HttpStatus.OK)
    }

    @ExceptionHandler(UserAlreadyExistException::class)
    fun handleException(ex: UserAlreadyExistException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.CONFLICT, "userId: ${ex.userId} is Already Exist")
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleException(ex: UserNotFoundException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, "userId: ${ex.userId} is Not Found")
    }

    data class RegisterUserBody(val username: String)
}