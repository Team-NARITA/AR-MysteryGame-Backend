package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.exception.UserAlreadyExistException
import com.teamnarita.armysterygamebackend.exception.UserNotFoundException
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.service.user.IGameUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.ErrorResponse
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
    fun registerUser(@AuthenticationPrincipal principal: UserDetailsImpl, @RequestBody requestBody: RegisterUserBody): ResponseEntity<GameUser> {
        val gameUser = gameUserService.register(principal.gameUser.userId, requestBody.username)
        return ResponseEntity(gameUser, HttpStatus.OK)
    }

    @GetMapping("/")
    fun getUser(@AuthenticationPrincipal principal: UserDetailsImpl, @RequestBody requestBody: GetUserRequestBody): ResponseEntity<GameUser> {
        val gameUser = gameUserService.getUser(requestBody.userId)
        return ResponseEntity(gameUser, HttpStatus.OK)
    }

    @ExceptionHandler(UserAlreadyExistException::class)
    fun handleException(ex: UserAlreadyExistException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.CONFLICT, "userId: ${ex.userId} is Already Exist")
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleException(ex: UserNotFoundException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, "userId: ${ex.userId} is Not Found")
    }

    data class RegisterUserBody(var username: String)
    data class GetUserRequestBody(var userId: String)
}