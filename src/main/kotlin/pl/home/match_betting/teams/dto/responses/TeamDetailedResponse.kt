package pl.home.match_betting.teams.dto.responses

import pl.home.match_betting.groups.domain.Group
import pl.home.match_betting.teams.domain.Team

data class TeamDetailedResponse(
    val id: Long,
    val name: String,
    val group: Group
)

fun Team.toDetailedResponse(): TeamDetailedResponse = TeamDetailedResponse(id, name, group)