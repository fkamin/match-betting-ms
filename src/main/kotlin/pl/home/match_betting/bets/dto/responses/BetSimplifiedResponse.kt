package pl.home.match_betting.bets.dto.responses

import pl.home.match_betting.bets.domain.Bet
import pl.home.match_betting.matches.domain.TeamChoice

data class BetSimplifiedResponse(
    val id: Long,
    val leftTeamScorePrediction: Int,
    val rightTeamScorePrediction: Int,
    val winnerPrediction: TeamChoice
)

fun Bet.toSimplifiedResponse(): BetSimplifiedResponse
        = BetSimplifiedResponse(id, leftTeamScorePrediction, rightTeamScorePrediction, winnerPrediction)