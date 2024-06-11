package pl.home.match_betting.users.dto.responses

import pl.home.match_betting.bets.domain.Bet

data class UserDetailedResponse(
    val id: Long,
    val name: String,
    val totalPoints: Int,
    val betList: List<Bet>
)