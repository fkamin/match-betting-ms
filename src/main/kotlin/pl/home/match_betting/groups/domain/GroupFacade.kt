package pl.home.match_betting.groups.domain

import org.springframework.stereotype.Service
import pl.home.match_betting.groups.dto.exceptions.GroupAlreadyExistsException
import pl.home.match_betting.groups.dto.exceptions.GroupDoesNotExistsException
import pl.home.match_betting.groups.dto.requests.GroupCreationRequest
import pl.home.match_betting.groups.dto.requests.GroupUpdateRequest
import pl.home.match_betting.groups.dto.responses.GroupDetailedResponse
import pl.home.match_betting.groups.dto.responses.toDetailedResponse

@Service
class GroupFacade(
    private val groupRepository: GroupRepository) {
    fun create(groupCreationRequest: GroupCreationRequest): GroupDetailedResponse {
        if (groupRepository.existsByName(groupCreationRequest.name)) throw GroupAlreadyExistsException()

        val group = Group(name = groupCreationRequest.name)

        return groupRepository.save(group).toDetailedResponse()
    }

    fun findGroups(): List<GroupDetailedResponse> = groupRepository.findAll().map { it.toDetailedResponse() }

    fun findGroupById(groupId: String): GroupDetailedResponse {
        val group: Group = findById(groupId)
        return group.toDetailedResponse()
    }

    private fun findById(groupId: String): Group = groupRepository.findById(groupId.toLong()).orElseThrow { GroupDoesNotExistsException() }

    fun update(groupId: String, groupUpdateRequest: GroupUpdateRequest): GroupDetailedResponse {
        val group: Group = findById(groupId)

        group.name = groupUpdateRequest.name

        return groupRepository.save(group).toDetailedResponse()
    }

    fun delete(groupId: String): Unit = groupRepository.delete(findById(groupId))

}