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
@RequestMapping("/match-betting/groups")
class GroupController(
    private val groupFacade: GroupFacade) {

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addGroup(@RequestBody groupCreationRequest: GroupCreationRequest): ResponseEntity<GroupDetailedResponse> {
        return ResponseEntity.ok(groupFacade.create(groupCreationRequest))
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getGroups(): ResponseEntity<List<GroupDetailedResponse>> {
        return ResponseEntity.ok(groupFacade.findGroups())
    }

    @GetMapping("/{groupId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getGroup(@PathVariable groupId: String): ResponseEntity<GroupDetailedResponse> {
        return ResponseEntity.ok(groupFacade.findGroupById(groupId))
    }

    @PutMapping("/{groupId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun updateGroup(
        @PathVariable groupId: String,
        @RequestBody groupUpdateRequest: GroupUpdateRequest): ResponseEntity<GroupDetailedResponse> {
        return ResponseEntity.ok(groupFacade.update(groupId, groupUpdateRequest))
    }

    @DeleteMapping("/{groupId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun deleteGroup(@PathVariable groupId: String): ResponseEntity<Unit> {
        groupFacade.delete(groupId)
        return ResponseEntity.noContent().build()
    }

}