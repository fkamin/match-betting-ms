package pl.home.match_betting.auths

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HashConfiguration {

    @Bean
    fun hashFacade(): HashFacade = HashFacade()
}