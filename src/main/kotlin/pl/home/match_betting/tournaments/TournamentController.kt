package pl.home.match_betting.tournaments

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
import pl.home.match_betting.tournaments.domain.TournamentFacade
import pl.home.match_betting.tournaments.dto.requests.TournamentCreationRequest
import pl.home.match_betting.tournaments.dto.requests.TournamentUpdateRequest
import pl.home.match_betting.tournaments.dto.responses.TournamentDetailedResponse
import pl.home.match_betting.tournaments.dto.responses.TournamentSimplifiedResponse

@RestController
@RequestMapping("/match-betting/tournaments")
class TournamentController(private val tournamentFacade: TournamentFacade) {

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addTournament(@RequestBody tournamentCreationRequest: TournamentCreationRequest): TournamentDetailedResponse {
        return tournamentFacade.create(tournamentCreationRequest)
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    fun getTournaments(): List<TournamentSimplifiedResponse> {
        return tournamentFacade.findTournaments()
    }

    @GetMapping("/{tournamentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    fun getTournament(@PathVariable tournamentId: String): TournamentDetailedResponse {
        return tournamentFacade.findTournament(tournamentId)
    }

    @PutMapping("/{tournamentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    fun update(
        @PathVariable tournamentId: String,
        @RequestBody tournamentUpdateRequest: TournamentUpdateRequest): TournamentDetailedResponse {
        return tournamentFacade.update(tournamentId, tournamentUpdateRequest)
    }

    @DeleteMapping("/{tournamentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    fun deleteTournament(@PathVariable tournamentId: String): ResponseEntity<Unit> {
        tournamentFacade.delete(tournamentId)
        return ResponseEntity.noContent().build()
    }

}