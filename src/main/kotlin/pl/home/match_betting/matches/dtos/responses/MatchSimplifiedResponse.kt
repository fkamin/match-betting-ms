package pl.home.match_betting.matches.dtos.responses

import pl.home.match_betting.matches.domain.Match
import pl.home.match_betting.matches.domain.TeamChoice
import pl.home.match_betting.teams.domain.Team
import java.time.LocalDateTime

data class MatchSimplifiedResponse(
    val id: Long,
    val teamLeft: Team,
    val teamRight: Team,
    val date: LocalDateTime,
    val leftTeamScore: Int?,
    val rightTeamScore: Int?,
    var matchTeamResult: TeamChoice?,
)

fun Match.toSimplifiedResponse():
        MatchSimplifiedResponse = MatchSimplifiedResponse(id, leftTeam, rightTeam, date, leftTeamScore, rightTeamScore, matchTeamResult)