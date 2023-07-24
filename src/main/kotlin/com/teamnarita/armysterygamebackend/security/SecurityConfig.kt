package com.teamnarita.armysterygamebackend.security

import com.teamnarita.armysterygamebackend.repository.IUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(private val userRepository: IUserRepository) {
    @Bean
    fun securityFilterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        http.authorizeHttpRequests {
            it.anyRequest().authenticated()
        }

        http.addFilter(preAuthenticatedProcessingFilter(authenticationManager))

        http.csrf { it.disable() }
        http.cors { it.configurationSource(getCorsConfigurationSource()) }

        http.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        return http.build()
    }

    @Bean
    fun preAuthenticatedAuthenticationProvider(): PreAuthenticatedAuthenticationProvider {
        val preAuthenticatedAuthenticationProvider = PreAuthenticatedAuthenticationProvider()
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(AuthService(userRepository))
        preAuthenticatedAuthenticationProvider.setUserDetailsChecker(AccountStatusUserDetailsChecker())
        return preAuthenticatedAuthenticationProvider
    }
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    private fun getCorsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()

        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL)
        corsConfiguration.allowCredentials = false
        val corsSource = UrlBasedCorsConfigurationSource()
        corsSource.registerCorsConfiguration("/**", corsConfiguration)
        return corsSource
    }

    private fun preAuthenticatedProcessingFilter(authenticationManager: AuthenticationManager): AbstractPreAuthenticatedProcessingFilter {
        val preAuthenticatedProcessingFilter = AuthFilter()
        preAuthenticatedProcessingFilter.setAuthenticationManager(authenticationManager)
        return preAuthenticatedProcessingFilter
    }
}