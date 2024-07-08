package pl.home.match_betting.auths

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.home.match_betting.auths.domain.AuthFacade
import pl.home.match_betting.auths.dto.responses.AuthenticationResponse
import pl.home.match_betting.users.dto.requests.CreateUserRequest
import pl.home.match_betting.users.dto.requests.LoginRequest
import pl.home.match_betting.users.dto.responses.NewUserResponse

@RestController
@RequestMapping("/match-betting/auth")
data class AuthController(private val authFacade: AuthFacade) {

    @PostMapping("/register")
    fun register(@RequestBody createUserRequest: CreateUserRequest): NewUserResponse {
        return authFacade.register(createUserRequest)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authFacade.login(loginRequest))
    }
}