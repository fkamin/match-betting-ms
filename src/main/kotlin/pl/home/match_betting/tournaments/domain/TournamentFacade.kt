package pl.home.match_betting.tournaments.domain

import org.springframework.stereotype.Service
import pl.home.match_betting.tournaments.dto.exceptions.TournamentIsNotInCreationStageException
import pl.home.match_betting.tournaments.dto.exceptions.TournamentDoesNotExistsException
import pl.home.match_betting.tournaments.dto.exceptions.TournamentIsNotInBettingStageException
import pl.home.match_betting.tournaments.dto.requests.TournamentCreationRequest
import pl.home.match_betting.tournaments.dto.requests.TournamentUpdateRequest
import pl.home.match_betting.tournaments.dto.responses.TournamentDetailedResponse
import pl.home.match_betting.tournaments.dto.responses.TournamentSimplifiedResponse
import pl.home.match_betting.tournaments.dto.responses.toDetailedResponse
import pl.home.match_betting.tournaments.dto.responses.toSimplifiedResponse

@Service
class TournamentFacade(private val tournamentRepository: TournamentRepository) {

    fun create(tournamentCreationRequest: TournamentCreationRequest): TournamentDetailedResponse {
        //TODO walidacja nowego turnieju
        val tournament = Tournament(
            name = tournamentCreationRequest.name,
            competitionStage = tournamentCreationRequest.competitionStage
        )

        return tournamentRepository.save(tournament).toDetailedResponse()
    }

    fun findTournaments():
            List<TournamentSimplifiedResponse> = tournamentRepository.findAll().map { it.toSimplifiedResponse() }

    fun findTournament(tournamentId: String):
            TournamentDetailedResponse = findTournamentById(tournamentId).toDetailedResponse()

    fun update(tournamentId: String, tournamentUpdateRequest: TournamentUpdateRequest): TournamentDetailedResponse {
        if (!isTournamentInCreationStage(tournamentId)) throw TournamentIsNotInCreationStageException()

        val tournament: Tournament = findTournamentById(tournamentId)

        tournament.name = tournamentUpdateRequest.name
        tournament.competitionStage = tournamentUpdateRequest.competitionStage

        return tournamentRepository.save(tournament).toDetailedResponse()
    }

    fun delete(tournamentId: String) {
        if (!isTournamentInCreationStage(tournamentId)) throw TournamentIsNotInCreationStageException()
        tournamentRepository.delete(findTournamentById(tournamentId))
    }

    fun bettingPhase(tournamentId: String) {
        val tournament: Tournament = findTournamentById(tournamentId)
        tournament.bettingPhase()
        tournamentRepository.save(tournament)
    }

    fun runningPhase(tournamentId: String) {
        val tournament: Tournament = findTournamentById(tournamentId)
        if (tournament.tournamentStage != TournamentStage.BET_STAGE) throw TournamentIsNotInBettingStageException()
        tournament.runningPhase()
        tournamentRepository.save(tournament)
    }

    fun findTournamentById(tournamentId: String):
            Tournament = tournamentRepository.findById(tournamentId.toLong()).orElseThrow { TournamentDoesNotExistsException() }

    fun isTournamentInCreationStage(tournamentId: String): Boolean {
        val tournament: Tournament = findTournamentById(tournamentId)
        return tournament.tournamentStage == TournamentStage.CREATION_STAGE
    }

    fun isTournamentInBettingStage(tournamentId: String): Boolean {
        val tournament: Tournament = findTournamentById(tournamentId)
        return tournament.tournamentStage == TournamentStage.BET_STAGE
    }

}