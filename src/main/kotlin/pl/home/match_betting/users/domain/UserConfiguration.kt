package pl.home.match_betting.users.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.home.match_betting.auths.HashFacade
import pl.home.match_betting.auths.JwtFacade

@Configuration
class UserConfiguration {

    @Bean
    fun userFacade(
        repository: UserRepository,
        hashFacade: HashFacade,
        jwtFacade: JwtFacade): UserFacade {

        return UserFacade(userRepository = repository, hashFacade = hashFacade, jwtFacade = jwtFacade)
    }
}