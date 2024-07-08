package pl.home.match_betting.auths.domain

import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.home.match_betting.auths.JwtFacade
import pl.home.match_betting.auths.dto.responses.AuthenticationResponse
import pl.home.match_betting.users.domain.Role
import pl.home.match_betting.users.domain.User
import pl.home.match_betting.users.domain.UserRepository
import pl.home.match_betting.users.dto.exceptions.UserAlreadyExistsException
import pl.home.match_betting.users.dto.exceptions.UserNotFoundException
import pl.home.match_betting.users.dto.requests.CreateUserRequest
import pl.home.match_betting.users.dto.requests.LoginRequest
import pl.home.match_betting.users.dto.responses.NewUserResponse
import java.util.*
import kotlin.math.log

@Service
data class AuthFacade(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtFacade: JwtFacade,
    private val authenticationManager: AuthenticationManager) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun register(createUserRequest: CreateUserRequest): NewUserResponse {
        if (userRepository.existsUserByLogin(createUserRequest.login)) throw UserAlreadyExistsException()
        val generatedPassword: String = generatePassword()

        val user = User(
            name = createUserRequest.name,
            login = createUserRequest.login,
            encodedPassword = passwordEncoder.encode(generatedPassword),
            role = Role.USER
        )

        userRepository.save(user)
        return NewUserResponse(name = user.name, login = user.login, password = generatedPassword)
    }

    fun login(loginRequest: LoginRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.login, loginRequest.password
            )
        )

        val user: User = findUserByLogin(loginRequest.login)
        val jwtToken: String = jwtFacade.generateToken(user)

        logger.info(jwtToken)

        return AuthenticationResponse(token = jwtToken)
    }

    private fun generatePassword(): String = UUID.randomUUID().toString().replace("-", "").substring(0, 8)

    private fun findUserByLogin(login: String): User = userRepository.findUserByLogin(login).orElseThrow{ UserNotFoundException() }
}