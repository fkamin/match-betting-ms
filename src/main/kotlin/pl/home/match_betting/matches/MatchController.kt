package pl.home.match_betting.matches

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
import pl.home.match_betting.matches.domain.MatchFacade
import pl.home.match_betting.matches.dtos.requests.MatchCreationRequest
import pl.home.match_betting.matches.dtos.requests.MatchScoreUpdateRequest
import pl.home.match_betting.matches.dtos.requests.MatchUpdateRequest
import pl.home.match_betting.matches.dtos.responses.MatchDetailedResponse
import pl.home.match_betting.matches.dtos.responses.MatchSimplifiedResponse

@RestController
@RequestMapping("/match-betting/tournaments")
class MatchController(private val matchFacade: MatchFacade) {

    @PostMapping("/{tournamentId}/matches")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun addMatch(
        @PathVariable tournamentId: String,
        @RequestBody matchCreationRequest: MatchCreationRequest): MatchDetailedResponse {
        return matchFacade.create(tournamentId, matchCreationRequest)
    }

    @GetMapping("/{tournamentId}/matches")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    fun getMatches(@PathVariable tournamentId: String): List<MatchSimplifiedResponse> {
        return matchFacade.findMatches(tournamentId)
    }

    @GetMapping("/{tournamentId}/matches/{matchId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    fun getMatch(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String): MatchDetailedResponse {
        return matchFacade.findMatch(tournamentId, matchId)
    }

    @PutMapping("/{tournamentId}/matches/{matchId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun updateMatch(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String,
        @RequestBody matchUpdateRequest: MatchUpdateRequest): MatchDetailedResponse {
        return matchFacade.update(tournamentId, matchId, matchUpdateRequest)
    }

    @PutMapping("/{tournamentId}/matches/{matchId}/score")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun updateMatchScore(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String,
        @RequestBody matchScoreUpdateRequest: MatchScoreUpdateRequest): MatchSimplifiedResponse {
        return matchFacade.updateScore(tournamentId, matchId, matchScoreUpdateRequest)
    }

    @DeleteMapping("/{tournamentId}/matches/{matchId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun delete(
        @PathVariable tournamentId: String,
        @PathVariable matchId: String): ResponseEntity<Unit> {
        matchFacade.delete(tournamentId, matchId)
        return ResponseEntity.noContent().build()
    }

}