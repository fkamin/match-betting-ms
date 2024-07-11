package pl.home.match_betting.bets.domain

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import pl.home.match_betting.auths.domain.SecurityHelper
import pl.home.match_betting.auths.domain.toUserId
import pl.home.match_betting.bets.dto.exceptions.BetDoesNotExistsExeption
import pl.home.match_betting.bets.dto.requests.BetCreationRequest
import pl.home.match_betting.bets.dto.requests.BetUpdateRequest
import pl.home.match_betting.bets.dto.responses.BetDetailedResponse
import pl.home.match_betting.bets.dto.responses.BetSimplifiedResponse
import pl.home.match_betting.bets.dto.responses.toDetailedResponse
import pl.home.match_betting.bets.dto.responses.toSimplifiedResponse
import pl.home.match_betting.matches.domain.Match
import pl.home.match_betting.matches.domain.MatchFacade
import pl.home.match_betting.tournaments.domain.Tournament
import pl.home.match_betting.tournaments.domain.TournamentFacade
import pl.home.match_betting.tournaments.domain.TournamentStage
import pl.home.match_betting.tournaments.dto.exceptions.TournamentIsNotInBettingStageException
import pl.home.match_betting.users.domain.User
import pl.home.match_betting.users.domain.UserFacade

@Service
class BetFacade(
    private val betRepository: BetRepository,
    private val tournamentFacade: TournamentFacade,
    private val userFacade: UserFacade,
    private val matchFacade: MatchFacade,
    private val securityHelper: SecurityHelper) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun create(
        tournamentId: String,
        matchId: String,
        betCreationRequest: BetCreationRequest,
        authContext: Authentication): BetDetailedResponse {

        val tournament: Tournament = tournamentFacade.findTournamentById(tournamentId)
        logger.info(tournament.tournamentStage.toString())

        if (tournament.tournamentStage != TournamentStage.BET_STAGE) throw TournamentIsNotInBettingStageException()

        val user: User = userFacade.findUserById(authContext.toUserId())
        val match: Match = matchFacade.findMatchById(matchId)

        val bet = Bet(
            user = user,
            match = match,
            leftTeamScorePrediction = betCreationRequest.leftTeamScorePrediction,
            rightTeamScorePrediction = betCreationRequest.rightTeamScorePrediction,
            winnerPrediction = matchFacade.getMatchResult(
                tournament.competitionStage,
                betCreationRequest.leftTeamScorePrediction,
                betCreationRequest.rightTeamScorePrediction,
                betCreationRequest.predictedWinner)
        )

        return betRepository.save(bet).toDetailedResponse()
    }

    fun findBets(tournamentId: String, matchId: String, authContext: Authentication): List<BetSimplifiedResponse> {
        val tournament: Tournament = tournamentFacade.findTournamentById(tournamentId)
        if (tournament.tournamentStage == TournamentStage.CREATION_STAGE) throw TournamentIsNotInBettingStageException()
        if (tournament.tournamentStage == TournamentStage.BET_STAGE) {
            val user: User = userFacade.findUserById(authContext.toUserId())
            return findAuthenticatedUserBets(user)
        }

        return findAllUsersBets()
    }

    fun findBet(tournamentId: String, matchId: String, betId: String, authContext: Authentication): BetDetailedResponse {
        if (tournamentFacade.isTournamentInCreationStage(tournamentId)) throw TournamentIsNotInBettingStageException()

        val bet: Bet = findBetById(betId)
        securityHelper.assertUserIsAuthorizedForResource(authContext, bet.user.id.toString())

        return bet.toDetailedResponse()
    }

    fun update(tournamentId: String, matchId: String, betId: String, betUpdateRequest: BetUpdateRequest, authContext: Authentication): BetDetailedResponse {
        val tournament: Tournament = tournamentFacade.findTournamentById(tournamentId)
        if (tournament.tournamentStage != TournamentStage.BET_STAGE) throw TournamentIsNotInBettingStageException()

        val bet: Bet = findBetById(betId)
        securityHelper.assertUserIsAuthorizedForResource(authContext, bet.user.id.toString())

        bet.leftTeamScorePrediction = betUpdateRequest.leftTeamScorePrediction
        bet.rightTeamScorePrediction = betUpdateRequest.rightTeamScorePrediction
        bet.winnerPrediction = matchFacade.getMatchResult(
            tournament.competitionStage,
            betUpdateRequest.leftTeamScorePrediction,
            betUpdateRequest.rightTeamScorePrediction,
            betUpdateRequest.predictedWinner
        )

        return betRepository.save(bet).toDetailedResponse()
    }

    fun delete(tournamentId: String, matchId: String, betId: String, authContext: Authentication) {
        val betToDelete: Bet = findBetById(betId)
        securityHelper.assertUserIsAuthorizedForResource(authContext, betToDelete.user.id.toString())
        betRepository.delete(betToDelete)
    }

    private fun findAuthenticatedUserBets(user: User): List<BetSimplifiedResponse> {
        return betRepository.findAllByUser(user).map { it.toSimplifiedResponse() }
    }

    private fun findAllUsersBets(): List<BetSimplifiedResponse> {
        return betRepository.findAll().map { it.toSimplifiedResponse() }
    }

    private fun findBetById(betId: String):
            Bet = betRepository.findById(betId.toLong()).orElseThrow { BetDoesNotExistsExeption() }
}