package pl.home.match_betting.auths.domain

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey
import kotlin.collections.HashMap

@Service
class JwtFacade {

    @Value("\${jwt.expiration.time}")
    private val jwtExpirationTime: Long = 0L

    @Value("\${jwt.signing.key}")
    private val jwtSigningKey: String = ""

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun generateToken(userDetails: UserDetails): String = buildToken(HashMap(), userDetails)

    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String = buildToken(extraClaims, userDetails)

    fun buildToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails
    ): String {

        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + jwtExpirationTime))
            .signWith(getSignInKey(), Jwts.SIG.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username: String = extractUserLogin(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun extractUserLogin(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun getSignInKey(): SecretKey {
        val keyBytes: ByteArray = Decoders.BASE64.decode(jwtSigningKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}