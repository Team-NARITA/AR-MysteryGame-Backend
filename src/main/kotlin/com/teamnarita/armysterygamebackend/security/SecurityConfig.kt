package com.teamnarita.armysterygamebackend.security

import com.teamnarita.armysterygamebackend.service.IGameUserService
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
class SecurityConfig(private val gameUserService: IGameUserService) {
    @Bean
    fun securityFilterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain {
        http
            .addFilter(preAuthenticatedProcessingFilter(authenticationManager))
            .csrf { it.disable() }
            .cors {
                it.configurationSource(getCorsConfigurationSource())
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers("/users/register").permitAll()
                    .anyRequest().authenticated()
            }

        return http.build()
    }

    @Bean
    fun preAuthenticatedAuthenticationProvider(): PreAuthenticatedAuthenticationProvider {
        val preAuthenticatedAuthenticationProvider = PreAuthenticatedAuthenticationProvider()
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(AuthService(gameUserService))
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