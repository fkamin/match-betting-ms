package pl.home.match_betting.auths

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import pl.home.match_betting.configs.SecurityConfig
import pl.home.match_betting.users.domain.User
import pl.home.match_betting.users.domain.UserFacade
import pl.home.match_betting.users.domain.UserRepository
import pl.home.match_betting.users.dto.exceptions.UserNotFoundException
import java.time.Instant

class JwtFacade(
    private val securityConfig: SecurityConfig,
    private val userRepository: UserRepository) {

    @Value("\${jwt.expiration.time}")
    private val jwtExpirationTime: Long = 0L

    fun createToken(user: User): String {
        val now: Instant = Instant.now()
        val expiry: Instant = now.plusSeconds(jwtExpirationTime)

        val claims: JwtClaimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(expiry)
            .subject(user.name)
//            .claim("roles", if (user.isAdmin) "ROLE_ADMIN" else "ROLE_USER")
            .claim("userId", user.id)
            .build()

        return securityConfig.jwtEncoder().encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    fun parseToken(token: String): User {
        val jwt: Jwt = securityConfig.jwtDecoder().decode(token)
        val userId: Long = jwt.claims["userId"] as Long
        return userRepository.findById(userId).orElseThrow{ UserNotFoundException() }
    }

}