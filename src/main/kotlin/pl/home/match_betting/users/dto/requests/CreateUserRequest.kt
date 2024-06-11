package pl.home.match_betting.users.dto.requests

data class CreateUserRequest(
    val login: String,
    val password: String
)