package pl.home.match_betting.auths.domain

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import pl.home.match_betting.users.domain.User
import pl.home.match_betting.users.dto.exceptions.UserUnauthorizedException

@Component
class SecurityHelper {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun isCurrentUserAuthorizedForResources(authContext: Authentication, resourceOwnerId: String): Boolean {
        return authContext.toUserId() == resourceOwnerId || authContext.toPlainTextRoles().contains("ROLE_ADMIN")
    }

    fun assertUserIsAuthorizedForResource(authContext: Authentication, resourceOwnerId: String) {
        if (!isCurrentUserAuthorizedForResources(authContext, resourceOwnerId)) {
            throw UserUnauthorizedException()
        }
    }
}

fun Authentication.toUserId(): String = (principal as User).id.toString()

private fun Authentication.toPlainTextRoles(): List<String> {
    return this.authorities.map { it.authority }.filter { it.startsWith("ROLE_") }
}