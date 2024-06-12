package pl.home.match_betting.users.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.home.match_betting.auths.JwtFacade

@Configuration
class UserConfiguration {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userFacade(
        repository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder,
        jwtFacade: JwtFacade): UserFacade {

        return UserFacade(userRepository = repository, passwordEncoder = passwordEncoder, jwtFacade = jwtFacade)
    }
}