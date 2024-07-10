package pl.home.match_betting.users.domain

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.home.match_betting.auths.domain.SecurityHelper
import pl.home.match_betting.users.dto.exceptions.UserNotFoundException
import pl.home.match_betting.users.dto.requests.UpdateUserPasswordRequest
import pl.home.match_betting.users.dto.responses.UserDetailedResponse
import pl.home.match_betting.users.dto.responses.toDetailedResponse

@Service
class UserFacade(
    private val userRepository: UserRepository,
    private val securityHelper: SecurityHelper,
    private val passwordEncoder: PasswordEncoder) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun changePassword(userId: String, payload: UpdateUserPasswordRequest, authContext: Authentication): ResponseEntity<String> {
        val user: User = findUserById(userId)
        securityHelper.assertUserIsAuthorizedForResource(authContext, user.id.toString())

        // TODO weryfikacja nowego hasła
        user.encodedPassword = passwordEncoder.encode(payload.newPassword)

        userRepository.save(user)

       // TODO zmiana return na cos w stylu UserDetailedReponse
        return ResponseEntity.ok("Pomyslnie zmieniono haslo")
    }

    fun findUserById(id: String): User = userRepository.findById(id.toLong()).orElseThrow{ UserNotFoundException() }
}


