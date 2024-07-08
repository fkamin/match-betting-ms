package pl.home.match_betting.users.domain

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

    fun changePassword(payload: UpdateUserPasswordRequest, authContext: Authentication): String {
        val user: User = findUserByLogin(payload.login)
        securityHelper.assertUserIsAuthorizedForResource(authContext, user.id.toString())

        // TODO weryfikacja nowego hasła
        user.encodedPassword = passwordEncoder.encode(payload.newPassword)

        userRepository.save(user)

        // TODO zmiana return na cos w stylu UserDetailedReponse
        return "Pomyslnie zmieniono haslo"
    }

    private fun findUserByLogin(login: String): User = userRepository.findUserByLogin(login).orElseThrow{ UserNotFoundException() }

    fun getAllUsers(): List<UserDetailedResponse> {
        return userRepository.findAll().map { user -> user.toDetailedResponse() }
    }
}