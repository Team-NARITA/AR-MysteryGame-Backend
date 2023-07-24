package com.teamnarita.armysterygamebackend.security

import jakarta.servlet.http.HttpServletRequest
import org.apache.http.HttpHeaders
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter

class AuthFilter: AbstractPreAuthenticatedProcessingFilter() {
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): Any {
        return ""
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): String {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return ""
        if (!token.startsWith("Bearer ")) return ""
        return token.substring("Bearer ".length)
    }
}