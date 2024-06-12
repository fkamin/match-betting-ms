package pl.home.match_betting.users.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun existsUserByLogin(login: String): Boolean
    fun findUserByLogin(login: String): Optional<User>
}