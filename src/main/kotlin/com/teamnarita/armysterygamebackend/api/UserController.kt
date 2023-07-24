package com.teamnarita.armysterygamebackend.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/users")
class UserController {
    @PostMapping("/register")
    fun registerUser(@RequestParam("userName") userName: String): String {
        return userName
    }
}