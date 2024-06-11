package pl.home.match_betting.users.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfiguration {

    @Bean
    fun userFacade(repository: UserRepository): UserFacade = UserFacade(userRepository = repository)
}