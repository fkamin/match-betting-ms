package pl.home.match_betting.matches.dtos.responses

import pl.home.match_betting.bets.domain.Bet
import pl.home.match_betting.matches.domain.Match
import pl.home.match_betting.matches.domain.TeamChoice
import pl.home.match_betting.teams.domain.Team
import java.time.LocalDateTime

data class MatchDetailedResponse(
    val id: Long,
    val teamLeft: Team,
    val teamRight: Team,
    val teamLeftScore: Int?,
    val teamRightScore: Int?,
    var matchTeamResult: TeamChoice?,
    val date: LocalDateTime,
    val bets: List<Bet>,
    val isFinished: Boolean
)

fun Match.toDetailedResponse():
        MatchDetailedResponse = MatchDetailedResponse(id, leftTeam, rightTeam, leftTeamScore, rightTeamScore, matchTeamResult, date, bets, isFinished)
