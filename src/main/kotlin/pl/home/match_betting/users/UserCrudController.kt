package pl.home.match_betting.users

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

@RestController
@RequestMapping("/match-betting/users")
class UserCrudController(private val userFacade: UserFacade) {

    @PostMapping("/register")
    fun addUser(@RequestBody payload: CreateUserRequest): NewUserResponse {
        return userFacade.generateUserAccount(payload)
    }


    @PostMapping("/login")
    fun login(@RequestBody payload: LoginRequest): String {
        return userFacade.loginUser(payload)
    }

    @PutMapping("/change-password")
    fun changePassword(@RequestBody payload: UpdateUserPasswordRequest): String {
        return userFacade.changePassword(payload)
    }

//    @PostMapping("/auth/logout")
//    fun logout(@RequestBody payload: LoginRequest): ResponseEntity<Unit> {
//        return ResponseEntity.noContent().build()
//    }

}