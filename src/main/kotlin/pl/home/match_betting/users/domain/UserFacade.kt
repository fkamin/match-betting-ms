package pl.home.match_betting.users.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.home.match_betting.auths.HashFacade
import pl.home.match_betting.users.dto.exceptions.UserAlreadyExistsException
import pl.home.match_betting.users.dto.exceptions.UserNotFoundException
import pl.home.match_betting.users.dto.exceptions.UserUnauthorizedException
import pl.home.match_betting.users.dto.requests.CreateUserRequest
import pl.home.match_betting.users.dto.requests.LoginRequest
import pl.home.match_betting.users.dto.requests.UpdateUserPasswordRequest
import pl.home.match_betting.users.dto.responses.NewUserResponse
import java.util.*

@Service
class UserFacade(
    private val userRepository: UserRepository,
    private val hashFacade: HashFacade
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun generateUserAccount(payload: CreateUserRequest): NewUserResponse {
        if (userRepository.existsUserByLogin(payload.login)) throw UserAlreadyExistsException()
        val generatedPassword: String = generatePassword()

        val user = User(
            name = payload.name,
            login = payload.login,
            password = hashFacade.hashBCrypt(generatedPassword)
        )

        val savedUser: User = userRepository.save(user)
        return NewUserResponse(savedUser.name, savedUser.login, generatedPassword)
    }

    private fun generatePassword(): String = UUID.randomUUID().toString().replace("-", "").substring(0, 8)

//    fun findUserById(id: Long): User = userRepository.findById(id).orElseThrow { UserNotFoundException() }



    fun loginUser(payload: LoginRequest): String {
        val user: User = findUserByLogin(payload.login)

        if (!hashFacade.checkBCrypt(payload.password, user.password)) throw UserUnauthorizedException()

        return "Zalogowano"
    }

    fun changePassword(payload: UpdateUserPasswordRequest): String {
        // TODO weryfikacja uzytkownika poprzez przyszłą autoryzacje
        val user: User = findUserByLogin(payload.login)

        if (!hashFacade.checkBCrypt(payload.currentPassword, user.password)) throw UserUnauthorizedException()

        // TODO weryfikacja nowego hasła
        user.password = hashFacade.hashBCrypt(payload.newPassword)

        // TODO zmiana return na cos w stylu UserDetailedReponse
        userRepository.save(user)

        return "Pomyslnie zmieniono haslo"
    }

    private fun findUserByLogin(login: String): User = userRepository.findUserByLogin(login).orElseThrow{ UserNotFoundException() }
}