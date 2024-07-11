package pl.home.match_betting.bets.dto.responses

import pl.home.match_betting.bets.domain.Bet
import pl.home.match_betting.matches.domain.TeamChoice

data class BetDetailedResponse(
    val id: Long,
    val userId: Long,
    val matchId: Long,
    val leftTeamScorePrediction: Int,
    val rightTeamScorePrediction: Int,
    val winnerPrediction: TeamChoice
)

fun Bet.toDetailedResponse():
        BetDetailedResponse = BetDetailedResponse(id, user.id, match.id, leftTeamScorePrediction, rightTeamScorePrediction, winnerPrediction)