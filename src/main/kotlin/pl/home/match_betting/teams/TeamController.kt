package pl.home.match_betting.teams

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
import pl.home.match_betting.teams.domain.TeamFacade
import pl.home.match_betting.teams.dto.requests.TeamCreationRequest
import pl.home.match_betting.teams.dto.requests.TeamUpdateRequest
import pl.home.match_betting.teams.dto.responses.TeamDetailedResponse

@RestController
@RequestMapping("/match-betting/groups")
class TeamController(private val teamFacade: TeamFacade) {

    @PostMapping("/{groupId}/teams")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addTeam(
        @PathVariable groupId: String,
        @RequestBody teamCreationRequest: TeamCreationRequest): ResponseEntity<TeamDetailedResponse> {
        return ResponseEntity.ok(teamFacade.create(groupId, teamCreationRequest))
    }

    @GetMapping("/{groupId}/teams")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getTeams(@PathVariable groupId: String): ResponseEntity<List<TeamDetailedResponse>> {
        return ResponseEntity.ok(teamFacade.findTeams(groupId))
    }

    @GetMapping("/{groupId}/teams/{teamId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getTeam(
        @PathVariable groupId: String,
        @PathVariable teamId: String): ResponseEntity<TeamDetailedResponse> {
        return ResponseEntity.ok(teamFacade.findTeam(groupId, teamId))
    }

    @PutMapping("/{groupId}/teams/{teamId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun update(
        @PathVariable groupId: String,
        @PathVariable teamId: String,
        @RequestBody teamUpdateRequest: TeamUpdateRequest): ResponseEntity<TeamDetailedResponse> {
        return ResponseEntity.ok(teamFacade.update(groupId, teamId, teamUpdateRequest))
    }

    @DeleteMapping("/{groupId}/teams/{teamId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun delete(
        @PathVariable groupId: String,
        @PathVariable teamId: String): ResponseEntity<TeamDetailedResponse> {
        teamFacade.delete(groupId, teamId)
        return ResponseEntity.noContent().build()
    }
}