package pl.home.match_betting.users.dto.responses

import pl.home.match_betting.bets.domain.Bet

data class UserDetailedResponse(
    val name: String,
    val totalPoints: String,
    val betList: List<Bet>
)