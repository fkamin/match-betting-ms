package pl.home.match_betting.users.dto.requests

data class LoginRequest(
    val login: String,
    val password: String
)