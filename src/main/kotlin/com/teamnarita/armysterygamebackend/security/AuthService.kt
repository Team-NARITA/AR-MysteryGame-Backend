package com.teamnarita.armysterygamebackend.security

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.teamnarita.armysterygamebackend.model.GameUser
import com.teamnarita.armysterygamebackend.model.UserDetailsImpl
import com.teamnarita.armysterygamebackend.service.user.IGameUserService
import com.teamnarita.armysterygamebackend.utility.TimeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired private val gameUserService: IGameUserService):
    AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    val timeUtil = TimeUtil

    private val firebaseApp = FirebaseAuth.getInstance()
    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken): UserDetailsImpl {
        val credential = token.credentials.toString()

        if (credential.isEmpty()) throw BadCredentialsException("トークンが空です")

        try {
            val firebaseToken = firebaseApp.verifyIdToken(credential)
            if (gameUserService.userExist(firebaseToken.uid)) {
                val gameUser = gameUserService.getUser(firebaseToken.uid)
                return UserDetailsImpl(gameUser)
            } else {
                val gameUser = GameUser.GameUserBuilder(
                    firebaseToken.uid, "unregisterUser", timeUtil.getCurrentTimeStamp(), GameUser.UserRole.UNREGISTER_USER
                ).build()
                return UserDetailsImpl(gameUser)
            }
        } catch (e: FirebaseException) {
            println("error")
            throw BadCredentialsException("トークン検証エラー", e)
        }
    }
}
