package pl.home.match_betting.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories
class RedisConfig(
    @Value("\${spring.data.redis.host}") val redisHost: String,
    @Value("\${spring.data.redis.port}") val redisPort: Int
) {

    @Bean
    fun connectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(redisHost, redisPort)
}