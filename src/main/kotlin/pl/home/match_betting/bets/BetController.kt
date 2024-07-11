package pl.home.match_betting.bets

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.CurrentSecurityContext
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.home.match_betting.auths.domain.toUserId
import pl.home.match_betting.bets.domain.BetFacade
import pl.home.match_betting.bets.dto.requests.BetCreationRequest
import pl.home.match_betting.bets.dto.requests.BetUpdateRequest
import pl.home.match_betting.bets.dto.responses.BetDetailedResponse
import pl.home.match_betting.bets.dto.responses.BetSimplifiedResponse

@RestController
@RequestMapping("/match-betting/tournaments")
class BetController(private val betFacade: BetFacade) {

    //TODO docelowo zmienic na ROLE_USER
    @PostMapping("/{tournamentId}/matches/{matchId}/bets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addBet(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String,
        @RequestBody betCreationRequest: BetCreationRequest,
        @CurrentSecurityContext(expression = "authentication") authContext: Authentication): BetDetailedResponse {
        return betFacade.create(tournamentId, matchId, betCreationRequest, authContext)
    }

    @GetMapping("/{tournamentId}/matches/{matchId}/bets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getBets(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String,
        @CurrentSecurityContext(expression = "authentication") authContext: Authentication): List<BetSimplifiedResponse> {
        return betFacade.findBets(tournamentId, matchId, authContext)
    }

    @GetMapping("/{tournamentId}/matches/{matchId}/bets/{betId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getBet(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String,
        @PathVariable betId: String,
        @CurrentSecurityContext(expression = "authentication") authContext: Authentication): BetDetailedResponse {
        return betFacade.findBet(tournamentId, matchId, betId, authContext)
    }

    @PutMapping("/{tournamentId}/matches/{matchId}/bets/{betId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun update(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String,
        @PathVariable betId: String,
        @RequestBody betUpdateRequest: BetUpdateRequest,
        @CurrentSecurityContext(expression = "authentication") authContext: Authentication): BetDetailedResponse {
        return betFacade.update(tournamentId, matchId, betId, betUpdateRequest, authContext)
    }

    @DeleteMapping("/{tournamentId}/matches/{matchId}/bets/{betId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun delete(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String,
        @PathVariable betId: String,
        @CurrentSecurityContext(expression = "authentication") authContext: Authentication): ResponseEntity<Unit> {
        betFacade.delete(tournamentId, matchId, betId, authContext)
        return ResponseEntity.noContent().build()
    }
}