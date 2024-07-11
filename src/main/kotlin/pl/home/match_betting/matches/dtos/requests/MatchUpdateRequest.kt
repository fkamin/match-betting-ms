package pl.home.match_betting.matches.dtos.requests

import java.time.LocalDateTime

data class MatchUpdateRequest(
    val teamLeftId: String,
    val teamRightId: String,
    val date: LocalDateTime
)