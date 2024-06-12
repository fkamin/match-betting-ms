package pl.home.match_betting.users.domain

import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.home.match_betting.auths.JwtFacade
import pl.home.match_betting.users.dto.exceptions.UserAlreadyExistsException
import pl.home.match_betting.users.dto.exceptions.UserNotFoundException
import pl.home.match_betting.users.dto.exceptions.UserUnauthorizedException
import pl.home.match_betting.users.dto.requests.CreateUserRequest
import pl.home.match_betting.users.dto.requests.LoginRequest
import pl.home.match_betting.users.dto.responses.NewUserResponse
import java.util.*

class UserFacade(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtFacade: JwtFacade) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun generateUserAccount(payload: CreateUserRequest): NewUserResponse {
        if (userRepository.existsUserByLogin(payload.login)) throw UserAlreadyExistsException()
        val generatedPassword: String = generatePassword()

        val user = User(
            name = payload.name,
            login = payload.login,
            password = passwordEncoder.encode(generatedPassword)
        )

        val savedUser: User = userRepository.save(user)
        return NewUserResponse(savedUser.name, savedUser.login, generatedPassword)
    }

    private fun generatePassword(): String = UUID.randomUUID().toString().replace("-", "").substring(0, 8)

//    fun findUserById(id: Long): User = userRepository.findById(id).orElseThrow { UserNotFoundException() }



    fun loginUser(payload: LoginRequest): String {
        val user: User = findUserByLogin(payload.login)
        logger.info(user.toString())

        logger.info(user.password)
        logger.info(passwordEncoder.encode(payload.password))
//        if (!passwordEncoder.matches(payload.password, user.password)) throw UserUnauthorizedException()
//        logger.info(passwordEncoder.matches(payload.password, user.password).toString())

        return jwtFacade.createToken(user)
    }

    private fun findUserByLogin(login: String): User = userRepository.findUserByLogin(login).orElseThrow{ UserNotFoundException() }
}