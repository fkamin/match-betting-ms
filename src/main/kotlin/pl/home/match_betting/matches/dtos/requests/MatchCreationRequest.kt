package pl.home.match_betting.matches.dtos.requests

import java.time.LocalDateTime

data class MatchCreationRequest(
    val leftTeamId: String,
    val rightTeamId: String,
    val date: LocalDateTime
)
