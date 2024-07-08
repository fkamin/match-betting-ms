package pl.home.match_betting.users.dto.responses

import pl.home.match_betting.bets.domain.Bet
import pl.home.match_betting.users.domain.User

data class UserDetailedResponse(
    val name: String,
    val totalPoints: Int,
    val betList: List<Bet>
)

fun User.toDetailedResponse(): UserDetailedResponse = UserDetailedResponse(name, totalPoints, betList)