package pl.home.match_betting.groups.domain

import org.springframework.stereotype.Service
import pl.home.match_betting.groups.dto.exceptions.GroupAlreadyExistsException
import pl.home.match_betting.groups.dto.exceptions.GroupDoesNotExistsException
import pl.home.match_betting.groups.dto.requests.GroupCreationRequest
import pl.home.match_betting.groups.dto.requests.GroupUpdateRequest
import pl.home.match_betting.groups.dto.responses.GroupDetailedResponse
import pl.home.match_betting.groups.dto.responses.toDetailedResponse
import pl.home.match_betting.tournaments.domain.Tournament
import pl.home.match_betting.tournaments.domain.TournamentFacade
import pl.home.match_betting.tournaments.dto.exceptions.TournamentIsNotInCreationStageException

@Service
class GroupFacade(
    private val groupRepository: GroupRepository,
    private val tournamentFacade: TournamentFacade) {
    fun create(tournamentId: String, groupCreationRequest: GroupCreationRequest): GroupDetailedResponse {
        if (!tournamentFacade.isTournamentInCreationStage(tournamentId)) throw TournamentIsNotInCreationStageException()

        if (groupRepository.existsByName(groupCreationRequest.name)) throw GroupAlreadyExistsException()

        val group = Group(name = groupCreationRequest.name)

        return groupRepository.save(group).toDetailedResponse()
    }

    fun findGroups(): List<GroupDetailedResponse> = groupRepository.findAll().map { it.toDetailedResponse() }

    fun findGroupDetails(groupId: String): GroupDetailedResponse = findGroupById(groupId).toDetailedResponse()

    fun update(tournamentId: String, groupId: String, groupUpdateRequest: GroupUpdateRequest): GroupDetailedResponse {
        if (!tournamentFacade.isTournamentInCreationStage(tournamentId)) throw TournamentIsNotInCreationStageException()

        val group: Group = findGroupById(groupId)

        group.name = groupUpdateRequest.name

        return groupRepository.save(group).toDetailedResponse()
    }

    fun delete(tournamentId: String, groupId: String) {
        if (!tournamentFacade.isTournamentInCreationStage(tournamentId)) throw TournamentIsNotInCreationStageException()
        groupRepository.delete(findGroupById(groupId))
    }

    private fun findGroupById(groupId: String): Group = groupRepository.findById(groupId.toLong()).orElseThrow { GroupDoesNotExistsException() }
}