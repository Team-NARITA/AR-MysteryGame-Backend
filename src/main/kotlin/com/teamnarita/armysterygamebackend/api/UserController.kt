package com.teamnarita.armysterygamebackend.api

import com.teamnarita.armysterygamebackend.service.GameUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/users")
class UserController {
    @Autowired
    private lateinit var gameUserService: GameUserService

    @PostMapping("/register")
    fun registerUser(@RequestParam("userName") userName: String): String {
        TODO()
    }
}