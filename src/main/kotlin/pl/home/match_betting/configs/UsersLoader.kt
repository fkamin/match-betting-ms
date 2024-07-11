package pl.home.match_betting.configs

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import pl.home.match_betting.users.domain.Role
import pl.home.match_betting.users.domain.User
import pl.home.match_betting.users.domain.UserRepository

@Component
class UsersLoader(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (!userRepository.existsUserByLogin("admin")) {
//            userRepository.save(User("admin", "admin", passwordEncoder.encode("admin123"), Role.ADMIN))
            userRepository.save(
                User(
                    login = "admin",
                    name = "admin",
                    encodedPassword = passwordEncoder.encode("admin123"),
                    role = Role.ADMIN
                ))
        }
    }
}