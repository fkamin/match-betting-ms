package pl.home.match_betting.auths

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.DigestAlgorithm
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import pl.home.match_betting.users.domain.UserRepository
import java.security.Key
import java.util.*
import javax.crypto.SecretKey
import kotlin.collections.HashMap
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Service
class JwtFacade(private val userRepository: UserRepository) {

    @Value("\${jwt.expiration.time}")
    private val jwtExpirationTime: Long = 0L

    @Value("\${jwt.signing.key}")
    private val jwtSigningKey: String = ""


    fun extractUserLogin(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimResolver: (Claims) -> T): T {
        val claims: Claims = extractAllClaims(token)
        return claimResolver(claims)
    }

    fun generateToken(userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)
    }

    fun generateToken(
        extraClaims: Map<String, JvmType.Object>,
        userDetails: UserDetails): String {

        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + jwtExpirationTime * 1000 * 60))
            .signWith(getSignInKey())
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username: String = extractUserLogin(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSignInKey(): SecretKey {
        val keyBytes: ByteArray = Decoders.BASE64.decode(jwtSigningKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}