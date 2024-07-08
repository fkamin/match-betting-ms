package pl.home.match_betting.auths

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class HashFacade {

    fun checkBCrypt(input: String, hash: String): Boolean = BCrypt.checkpw(input, hash)
    fun hashBCrypt(input: String): String = BCrypt.hashpw(input, BCrypt.gensalt(10))
}