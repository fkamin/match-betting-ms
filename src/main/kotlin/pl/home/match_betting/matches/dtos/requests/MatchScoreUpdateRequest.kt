package pl.home.match_betting.matches.dtos.requests

import pl.home.match_betting.matches.domain.TeamChoice

data class MatchScoreUpdateRequest(
    val leftTeamScore: Int,
    val rightTeamScore: Int,
    val teamChoice: TeamChoice? = null
)