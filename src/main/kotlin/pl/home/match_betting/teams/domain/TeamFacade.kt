package pl.home.match_betting.teams.domain

import org.springframework.stereotype.Service
import pl.home.match_betting.groups.domain.Group
import pl.home.match_betting.groups.domain.GroupRepository
import pl.home.match_betting.groups.dto.exceptions.GroupDoesNotExistsException
import pl.home.match_betting.teams.dto.exceptions.TeamAlreadyExistsException
import pl.home.match_betting.teams.dto.exceptions.TeamDoesNotExistsException
import pl.home.match_betting.teams.dto.requests.TeamCreationRequest
import pl.home.match_betting.teams.dto.requests.TeamUpdateRequest
import pl.home.match_betting.teams.dto.responses.TeamDetailedResponse
import pl.home.match_betting.teams.dto.responses.toDetailedResponse

@Service
class TeamFacade(
    private val teamRepository: TeamRepository,
    private val groupRepository: GroupRepository) {

    fun create(groupId: String, teamCreationRequest: TeamCreationRequest): TeamDetailedResponse {
        if (teamRepository.existsByName(teamCreationRequest.name)) throw TeamAlreadyExistsException()
        if (!groupRepository.existsById(groupId.toLong())) throw GroupDoesNotExistsException()

        val team = Team(
            name = teamCreationRequest.name,
            group = findGroupById(groupId)
        )

        return teamRepository.save(team).toDetailedResponse()
    }

    fun findTeams(groupId: String): List<TeamDetailedResponse> {
        val group: Group = findGroupById(groupId)
        return teamRepository.findAllByGroup(group).map { it.toDetailedResponse() }
    }

    fun findTeam(groupId: String, teamId: String): TeamDetailedResponse {
        val group: Group = findGroupById(groupId)
        return teamRepository.findByIdAndGroup(teamId.toLong(), group).orElseThrow { TeamDoesNotExistsException() }.toDetailedResponse()
    }

    fun update(groupId: String, teamId: String, teamUpdateRequest: TeamUpdateRequest): TeamDetailedResponse {
        val team: Team = findTeamByIdAndGroup(teamId, groupId)

        team.name = teamUpdateRequest.name

        return teamRepository.save(team).toDetailedResponse()
    }

    fun delete(groupId: String, teamId: String): Unit = teamRepository.deleteById(teamId.toLong())

    private fun findGroupById(groupId: String): Group {
        return groupRepository.findById(groupId.toLong()).orElseThrow { GroupDoesNotExistsException() }
    }

    private fun findTeamById(teamId: String): Team {
        return teamRepository.findById(teamId.toLong()).orElseThrow { TeamDoesNotExistsException() }
    }

    private fun findTeamByIdAndGroup(teamId: String, groupId: String): Team {
        val group: Group = findGroupById(groupId)
        return teamRepository.findByIdAndGroup(teamId.toLong(), group).orElseThrow { TeamDoesNotExistsException() }
    }
}