package pl.home.match_betting.users

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.home.match_betting.users.domain.UserFacade
import pl.home.match_betting.users.dto.requests.CreateUserRequest
import pl.home.match_betting.users.dto.responses.UserDetailedResponse

@RestController
@RequestMapping("/users")
class UserCrudController(private val userFacade: UserFacade) {

    @PostMapping("/new-user")
    fun addUser(@RequestBody payload: CreateUserRequest): UserDetailedResponse {

    }

    @PostMapping("/login")
    fun login(@RequestBody payload: LoginRequest): LoginResponse {

    }

    @PutMapping("/change-password")
    fun changePassword(@RequestBody payload: UpdateUserPasswordRequest): UserDetailedResponse {

    }

}