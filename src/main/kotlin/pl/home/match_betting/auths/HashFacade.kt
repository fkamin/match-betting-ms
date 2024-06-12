package pl.home.match_betting.auths

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

class HashFacade {

    fun checkBCrypt(input: String, hash: String): Boolean = BCrypt.checkpw(input, hash)
    fun hashBCrypt(input: String): String = BCrypt.hashpw(input, BCrypt.gensalt(10))
}