package pl.home.match_betting.auths

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.home.match_betting.configs.SecurityConfig
import pl.home.match_betting.users.domain.UserFacade
import pl.home.match_betting.users.domain.UserRepository

@Configuration
class JwtConfiguration {

    @Bean
    fun jwtFacade(
        securityConfig: SecurityConfig,
        userRepository: UserRepository): JwtFacade
            = JwtFacade(securityConfig = securityConfig, userRepository = userRepository)
}