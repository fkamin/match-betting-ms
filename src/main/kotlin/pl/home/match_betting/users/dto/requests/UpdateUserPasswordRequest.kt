package pl.home.match_betting.users.dto.requests

data class UpdateUserPasswordRequest(
    // TODO: zmienic z loginu na token
    val login: String,
    val currentPassword: String,
    val newPassword: String
)