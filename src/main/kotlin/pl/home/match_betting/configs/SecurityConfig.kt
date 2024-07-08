//package pl.home.match_betting.configs
//
//import com.nimbusds.jose.jwk.source.ImmutableSecret
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.convert.converter.Converter
//import org.springframework.http.HttpMethod
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.oauth2.jwt.*
//import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
//import org.springframework.security.provisioning.InMemoryUserDetailsManager
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.web.cors.CorsConfiguration
//import org.springframework.web.cors.CorsConfigurationSource
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//import org.springframework.web.filter.CorsFilter
//import javax.crypto.spec.SecretKeySpec
//
//const val JWT_SIGNING_ALGORITHM = "HmacSHA256"
//const val ROLES_KEY_IN_JWT = "roles"
//const val ROLE_PREFIX = "ROLE_"
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig {
//
////    @Value("\${jwt.signing.key}")
////    private lateinit var jwtSigningKey: String
//
//    private val AUTH_WHITELIST = arrayOf(
//        "/swagger-resources",
//        "/swagger-resources/**",
//        "/configuration/ui",
//        "/configuration/security",
//        "/swagger-ui.html",
//        "/webjars/**",
//        "/v3/api-docs/**",
//        "/api/public/**",
//        "/api/public/authenticate",
//        "/actuator/*",
//        "/swagger-ui/**"
//    )
//
//    private val PUBLIC_ENDPOINTS = arrayOf(
//        Endpoint(HttpMethod.POST, "/match-betting/users/login")
//    )
//
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
////        http.sessionManagement { sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
////
////        http.csrf { csrf -> csrf.disable() }
////
////        http.cors { cors ->
////            cors.configurationSource(corsConfigurationSource())
////        }
//
//        http.authorizeHttpRequests { authorize ->
//            AUTH_WHITELIST.forEach { path ->
//                authorize.requestMatchers(path).permitAll()
//            }
//            PUBLIC_ENDPOINTS.forEach { endpoint ->
//                authorize.requestMatchers(endpoint.method, endpoint.pattern).permitAll()
//            }
//            authorize.requestMatchers("/match-betting/users/register").hasRole("ADMIN")
//            authorize.anyRequest().authenticated()
//        }
//        http.formLogin { it.permitAll() }
//
//        return http.build()
//    }
//
//    @Bean
//    fun userDetailsService(): UserDetailsService {
//        val normalUser: UserDetails = User.builder()
//            .username("gc")
//            .password("\$2a\$12\$VKBvOrJb1Z.CEQ9cOc9p1e42CvkTR5HL8lAjJVsQYBUEyPbMZcK8a")
//            .roles("USER")
//            .build()
//
//        val adminUser: UserDetails = User.builder()
//            .username("admin")
//            .password("\$2a\$12\$bEylMw.1nQjA7rVwK.dfY.AuU0di.Vlkj3VGUw3SjLIA6bj6gMURW")
//            .roles("ADMIN")
//            .build()
//
//        return InMemoryUserDetailsManager(normalUser, adminUser)
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
////    @Bean
////    fun jwtEncoder(): JwtEncoder {
////        val originalKey = SecretKeySpec(jwtSigningKey.toByteArray(), JWT_SIGNING_ALGORITHM)
////        return NimbusJwtEncoder(ImmutableSecret(originalKey))
////    }
////
////    @Bean
////    fun jwtDecoder(): JwtDecoder {
////        val originalKey = SecretKeySpec(jwtSigningKey.toByteArray(), JWT_SIGNING_ALGORITHM)
////        return NimbusJwtDecoder.withSecretKey(originalKey).build()
////    }
////
////    @Bean
////    fun corsConfigurationSource(): CorsConfigurationSource {
////        val configuration = CorsConfiguration()
////        configuration.allowedOrigins = listOf("http://localhost:3000")
////        configuration.allowedMethods = listOf("*")
////        configuration.allowedHeaders = listOf("*")
////        configuration.exposedHeaders = listOf("*")
////        configuration.allowCredentials = true
////
////        val source = UrlBasedCorsConfigurationSource()
////        source.registerCorsConfiguration("/**", configuration)
////        return source
////    }
////
////    @Bean
////    fun corsFilter(): CorsFilter {
////        return CorsFilter(corsConfigurationSource())
////    }
//
////    internal class CustomAuthenticationConverter : Converter<Jwt, JwtAuthenticationToken> {
////        override fun convert(jwt: Jwt): JwtAuthenticationToken {
////            val userId: String = jwt.subject
////            val roles: List<GrantedAuthority> = jwt.getClaimAsStringList(ROLES_KEY_IN_JWT)
////                .map { SimpleGrantedAuthority("$ROLE_PREFIX$it") }
////            return JwtAuthenticationToken(jwt, roles, userId)
////        }
////    }
//
//    internal data class Endpoint(val method: HttpMethod, val pattern: String)
//}