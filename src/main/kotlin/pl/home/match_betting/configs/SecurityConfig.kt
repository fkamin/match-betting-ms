package pl.home.match_betting.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import pl.home.match_betting.auths.domain.JwtFilter
import pl.home.match_betting.users.domain.Role
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val authenticationProvider: AuthenticationProvider
) {

    private val AUTH_WHITELIST = arrayOf(
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/api/public/**",
        "/api/public/authenticate",
        "/actuator/*",
        "/actuator/**",
        "/actuator",
        "/swagger-ui/**",
        "/swagger-ui",
        "/swagger-ui/*",
        "/v3/api-docs"
    )

    private val PUBLIC_ENDPOINTS = arrayOf(
        Endpoint(HttpMethod.POST, "/match-betting/auth/**"),
//        Endpoint(HttpMethod.GET, "/match-betting/admin")
    )

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }

//        http.cors { cors ->
//            cors.configurationSource(corsConfigurationSource())
//        }

        http.authorizeHttpRequests { authorize ->
            AUTH_WHITELIST.forEach { path ->
                authorize.requestMatchers(path).permitAll()
            }
            PUBLIC_ENDPOINTS.forEach { endpoint ->
                authorize.requestMatchers(endpoint.method, endpoint.pattern).permitAll()
            }
//            authorize.requestMatchers("/match-betting/admin").hasRole(Role.ADMIN.name)
            authorize.anyRequest().authenticated()
        }

        http.sessionManagement { sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http.authenticationProvider(authenticationProvider)

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = listOf("http://localhost:3000")
//        configuration.allowedMethods = listOf("*")
//        configuration.allowedHeaders = listOf("*")
//        configuration.exposedHeaders = listOf("*")
//        configuration.allowCredentials = true
//
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }
//
//    @Bean
//    fun corsFilter(): CorsFilter {
//        return CorsFilter(corsConfigurationSource())
//    }


    internal data class Endpoint(val method: HttpMethod, val pattern: String)
}