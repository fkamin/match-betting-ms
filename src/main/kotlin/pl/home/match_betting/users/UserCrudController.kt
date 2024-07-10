package pl.home.match_betting.users

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.home.match_betting.users.domain.UserFacade
import pl.home.match_betting.users.dto.requests.CreateUserRequest
import pl.home.match_betting.users.dto.requests.LoginRequest
import pl.home.match_betting.users.dto.requests.UpdateUserPasswordRequest
import pl.home.match_betting.users.dto.responses.NewUserResponse
import pl.home.match_betting.users.dto.responses.UserDetailedResponse

@RestController
@RequestMapping("/match-betting/users")
class UserCrudController(private val userFacade: UserFacade) {


    @PutMapping("/{userId}/change-password")
//    @PreAuthorize("hasRole('ROLE_USER')")
    fun changePassword(
        @PathVariable userId: String,
        @RequestBody payload: UpdateUserPasswordRequest,
        @CurrentSecurityContext(expression = "authentication") authContext: Authentication): String {
        return userFacade.changePassword(userId, payload, authContext)
    }


    @GetMapping
    fun getAllUsers(): List<UserDetailedResponse> {
        return userFacade.getAllUsers()
    }
}