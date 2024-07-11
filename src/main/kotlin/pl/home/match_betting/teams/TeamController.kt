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
@RequestMapping("/match-betting/tournaments")
class TeamController(private val teamFacade: TeamFacade) {

    @PostMapping("/{tournamentId}/groups/{groupId}/teams")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addTeam(
        @PathVariable tournamentId: String,
        @PathVariable groupId: String,
        @RequestBody teamCreationRequest: TeamCreationRequest): TeamDetailedResponse {
        return teamFacade.create(tournamentId, groupId, teamCreationRequest)
    }

    @GetMapping("/{tournamentId}/groups/{groupId}/teams")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getTeams(
        @PathVariable tournamentId: String,
        @PathVariable groupId: String): List<TeamDetailedResponse> {
        return teamFacade.findTeams(groupId)
    }

    @GetMapping("/{tournamentId}/groups/{groupId}/teams/{teamId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getTeam(
        @PathVariable tournamentId: String,
        @PathVariable groupId: String,
        @PathVariable teamId: String): TeamDetailedResponse {
        return teamFacade.findTeamDetails(groupId, teamId)
    }

    @PutMapping("/{tournamentId}/groups/{groupId}/teams/{teamId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun update(
        @PathVariable tournamentId: String,
        @PathVariable groupId: String,
        @PathVariable teamId: String,
        @RequestBody teamUpdateRequest: TeamUpdateRequest): TeamDetailedResponse {
        return teamFacade.update(tournamentId, groupId, teamId, teamUpdateRequest)
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