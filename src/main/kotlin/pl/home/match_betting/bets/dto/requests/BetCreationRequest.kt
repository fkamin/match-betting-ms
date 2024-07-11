package pl.home.match_betting.bets.dto.requests

import pl.home.match_betting.matches.domain.TeamChoice

data class BetCreationRequest(
    val leftTeamScorePrediction: Int,
    val rightTeamScorePrediction: Int,
    val predictedWinner: TeamChoice
)