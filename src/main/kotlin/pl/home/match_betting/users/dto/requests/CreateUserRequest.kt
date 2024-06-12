package pl.home.match_betting.users.dto.requests

data class CreateUserRequest(
    val name: String,
    val login: String
)