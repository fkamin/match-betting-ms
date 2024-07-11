package pl.home.match_betting.groups

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.home.match_betting.groups.domain.GroupFacade
import pl.home.match_betting.groups.dto.requests.GroupCreationRequest
import pl.home.match_betting.groups.dto.requests.GroupUpdateRequest
import pl.home.match_betting.groups.dto.responses.GroupDetailedResponse

@RestController
@RequestMapping("/match-betting/tournaments")
class GroupController(
    private val groupFacade: GroupFacade) {

    @PostMapping("/{tournamentId}/groups")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addGroup(
        @PathVariable tournamentId: String,
        @RequestBody groupCreationRequest: GroupCreationRequest): GroupDetailedResponse {
        return groupFacade.create(tournamentId, groupCreationRequest)
    }

    @GetMapping("/{tournamentId}/groups")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getGroups(@PathVariable tournamentId: String): List<GroupDetailedResponse> {
        return groupFacade.findGroups()
    }

    @GetMapping("/{tournamentId}/groups/{groupId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getGroup(
        @PathVariable tournamentId: String,
        @PathVariable groupId: String): GroupDetailedResponse {
        return groupFacade.findGroupDetails(groupId)
    }

    @PutMapping("/{tournamentId}/groups/{groupId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun updateGroup(
        @PathVariable tournamentId: String,
        @PathVariable groupId: String,
        @RequestBody groupUpdateRequest: GroupUpdateRequest): GroupDetailedResponse {
        return groupFacade.update(tournamentId, groupId, groupUpdateRequest)
    }

    @DeleteMapping("/{tournamentId}/groups/{groupId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun deleteGroup(
        @PathVariable tournamentId: String,
        @PathVariable groupId: String): ResponseEntity<Unit> {
        groupFacade.delete(tournamentId, groupId)
        return ResponseEntity.noContent().build()
    }

}