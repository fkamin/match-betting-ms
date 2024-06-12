package pl.home.match_betting.users.dto.requests

data class UpdateUserPasswordRequest(
    val currentPassword: String,
    val newPassword: String
)