package pl.home.match_betting.matches.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.home.match_betting.bets.domain.Bet
import pl.home.match_betting.bets.domain.BetRepository
import pl.home.match_betting.bets.dto.exceptions.BetDoesNotExistsExeption
import pl.home.match_betting.matches.dtos.exceptions.MatchHasNotFinishedYetException
import pl.home.match_betting.users.domain.User
import pl.home.match_betting.users.domain.UserRepository

@Service
class PointsCalculationHelper(
    private val betRepository: BetRepository,
    private val userRepository: UserRepository) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun calculatePoints(match: Match, competitionStage: CompetitionStage) {
        val bets: List<Bet> = betRepository.findAllByMatch(match)
        val users: List<User> = bets.map { bet -> bet.user }

        if (!match.isFinished) throw MatchHasNotFinishedYetException()

        users.forEach { user ->
            globalPointsCalculation(user, match, competitionStage)
        }
    }

    private fun globalPointsCalculation(user: User, match: Match, competitionStage: CompetitionStage) {
        val userBet: Bet = findBetByUserAndMatch(user, match)
        var pointsScored = 0

        pointsScored += if (competitionStage == CompetitionStage.GROUP_STAGE) {
            calculatePointsForGroupStage(userBet, match)
        } else {
            calculatePointsForFinalStage(userBet, match)
        }

        user.increasePoints(pointsScored)
        userRepository.save(user)
    }

    private fun calculatePointsForGroupStage(userBet: Bet, match: Match): Int {
        if (userBet.leftTeamScorePrediction == match.leftTeamScore!!
            && userBet.rightTeamScorePrediction == match.rightTeamScore!!) return 3

        if (userBet.leftTeamScorePrediction < userBet.rightTeamScorePrediction
            && match.leftTeamScore!! < match.rightTeamScore!!) return 1

        if (userBet.leftTeamScorePrediction > userBet.rightTeamScorePrediction
            && match.leftTeamScore!! > match.rightTeamScore!!) return 1

        if (userBet.leftTeamScorePrediction == userBet.rightTeamScorePrediction
            && match.leftTeamScore!! == match.rightTeamScore!!) return 1

        return 0
    }

    private fun calculatePointsForFinalStage(userBet: Bet, match: Match): Int {
        val scoredPoints: Int = calculatePointsForGroupStage(userBet, match)

        return if (userBet.winnerPrediction == match.matchTeamResult) scoredPoints + 2
        else scoredPoints
    }

    private fun findBetByUserAndMatch(user: User, match: Match):
            Bet = betRepository.findBetByUserAndMatch(user, match).orElseThrow { BetDoesNotExistsExeption() }
}