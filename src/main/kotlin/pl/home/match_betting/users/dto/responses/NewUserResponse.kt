package pl.home.match_betting.users.dto.responses

data class NewUserResponse(
    val name: String,
    val login: String,
    val password: String
)
