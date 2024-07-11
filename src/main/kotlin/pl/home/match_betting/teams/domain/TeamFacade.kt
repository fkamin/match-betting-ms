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
import pl.home.match_betting.tournaments.domain.TournamentFacade
import pl.home.match_betting.tournaments.dto.exceptions.TournamentIsNotInCreationStageException

@Service
class TeamFacade(
    private val teamRepository: TeamRepository,
    private val groupRepository: GroupRepository,
    private val tournamentFacade: TournamentFacade) {

    fun create(tournamentId: String, groupId: String, teamCreationRequest: TeamCreationRequest): TeamDetailedResponse {
        if (!tournamentFacade.isTournamentInCreationStage(tournamentId)) throw TournamentIsNotInCreationStageException()
        if (!groupRepository.existsById(groupId.toLong())) throw GroupDoesNotExistsException()
        if (teamRepository.existsByName(teamCreationRequest.name)) throw TeamAlreadyExistsException()

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

    fun findTeamDetails(groupId: String, teamId: String): TeamDetailedResponse {
        val group: Group = findGroupById(groupId)
        return teamRepository.findByIdAndGroup(teamId.toLong(), group).orElseThrow { TeamDoesNotExistsException() }.toDetailedResponse()
    }

    fun update(tournamentId: String, groupId: String, teamId: String, teamUpdateRequest: TeamUpdateRequest): TeamDetailedResponse {
        if (!tournamentFacade.isTournamentInCreationStage(teamId)) throw TournamentIsNotInCreationStageException()
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