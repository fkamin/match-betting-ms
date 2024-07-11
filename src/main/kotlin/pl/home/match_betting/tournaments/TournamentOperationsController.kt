package pl.home.match_betting.tournaments

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.home.match_betting.tournaments.domain.TournamentFacade

@RestController
@RequestMapping("/match-betting/tournaments")
class TournamentOperationsController(private val tournamentFacade: TournamentFacade) {

    @PostMapping("/{tournamentId}/operations/start-betting-phase")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    fun bettingPhase(@PathVariable tournamentId: String): ResponseEntity<Unit> {
        tournamentFacade.bettingPhase(tournamentId)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("/{tournamentId}/operations/start-running-phase")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    fun runningPhase(@PathVariable tournamentId: String): ResponseEntity<Unit> {
        tournamentFacade.runningPhase(tournamentId)
        return ResponseEntity.accepted().build()
    }

}