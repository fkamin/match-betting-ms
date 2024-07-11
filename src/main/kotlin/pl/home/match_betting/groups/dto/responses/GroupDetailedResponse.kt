package pl.home.match_betting.groups.dto.responses

import pl.home.match_betting.groups.domain.Group
import pl.home.match_betting.teams.domain.Team

data class GroupDetailedResponse(
    val id: Long,
    val name: String,
    val teams: List<Team>
)

fun Group.toDetailedResponse(): GroupDetailedResponse = GroupDetailedResponse(id, name, teams)