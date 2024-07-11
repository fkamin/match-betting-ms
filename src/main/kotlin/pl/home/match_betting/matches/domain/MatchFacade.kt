package pl.home.match_betting.matches.domain

import org.springframework.stereotype.Service
import pl.home.match_betting.groups.domain.Group
import pl.home.match_betting.matches.dtos.exceptions.*
import pl.home.match_betting.matches.dtos.requests.MatchCreationRequest
import pl.home.match_betting.matches.dtos.requests.MatchScoreUpdateRequest
import pl.home.match_betting.matches.dtos.requests.MatchUpdateRequest
import pl.home.match_betting.matches.dtos.responses.MatchDetailedResponse
import pl.home.match_betting.matches.dtos.responses.MatchSimplifiedResponse
import pl.home.match_betting.matches.dtos.responses.toDetailedResponse
import pl.home.match_betting.matches.dtos.responses.toSimplifiedResponse
import pl.home.match_betting.teams.domain.Team
import pl.home.match_betting.teams.domain.TeamRepository
import pl.home.match_betting.teams.dto.exceptions.TeamDoesNotExistsException
import pl.home.match_betting.tournaments.domain.Tournament
import pl.home.match_betting.tournaments.domain.TournamentRepository
import pl.home.match_betting.tournaments.domain.TournamentStage
import pl.home.match_betting.tournaments.dto.exceptions.TournamentIsNotInCreationStageException
import pl.home.match_betting.tournaments.dto.exceptions.TournamentDoesNotExistsException
import pl.home.match_betting.tournaments.dto.exceptions.TournamentHasNotStartedYetException

@Service
class MatchFacade(
    private val tournamentRepository: TournamentRepository,
    private val matchRepository: MatchRepository,
    private val teamRepository: TeamRepository,
    private val pointsCalculationHelper: PointsCalculationHelper) {

    //TODO weryfikacja czy obie druzyny są w tej samej grupie w przypadku fazy grupowej
    fun create(tournamentId: String, matchCreationRequest: MatchCreationRequest): MatchDetailedResponse {
        val tournament: Tournament = findTournamentById(tournamentId)
        if (isTournamentAlreadyStarted(tournament)) throw TournamentIsNotInCreationStageException()

        if (areTwoTeamsTheSame(
                matchCreationRequest.leftTeamId,
                matchCreationRequest.rightTeamId)) throw TeamsInMatchCanNotBeTheSameException()

        val leftTeam: Team = findTeamById(matchCreationRequest.leftTeamId)
        val rightTeam: Team = findTeamById(matchCreationRequest.rightTeamId)
        if (!areTwoTeamsFromSameGroupInGroupStageMatch(
                leftTeam.group,
                rightTeam.group,
                tournament.competitionStage)) throw TeamsInGroupStageMatchMustBeFromTheSameGroupException()

        //TODO walidacja daty, nie moze byc wczesniejsza niz data dodawania
        val match = Match(
            tournament = tournament,
            leftTeam = leftTeam,
            rightTeam = rightTeam,
            date = matchCreationRequest.date
        )

        return matchRepository.save(match).toDetailedResponse()
    }

    fun findMatches(tournamentId: String): List<MatchSimplifiedResponse> {
        val tournament: Tournament = findTournamentById(tournamentId)
        return matchRepository.findAllByTournament(tournament).map { it.toSimplifiedResponse() }
    }

    fun findMatch(tournamentId: String, matchId: String): MatchDetailedResponse {
        val tournament: Tournament = findTournamentById(tournamentId)
        return findMatchByIdAndTournament(matchId, tournament).toDetailedResponse()
    }

    fun update(tournamentId: String, matchId: String, matchUpdateRequest: MatchUpdateRequest): MatchDetailedResponse {
        val tournament: Tournament = findTournamentById(tournamentId)
        if (isTournamentAlreadyStarted(tournament)) throw TournamentIsNotInCreationStageException()

        val match: Match = findMatchById(matchId)
        if (match.isFinished) throw MatchAlreadyFinishedException()

        //TODO walidacja nowego meczu, daty, ewentualnie druzyn
        match.leftTeam = findTeamById(matchUpdateRequest.teamLeftId)
        match.rightTeam = findTeamById(matchUpdateRequest.teamRightId)
        match.date = matchUpdateRequest.date

        return matchRepository.save(match).toDetailedResponse()
    }

    fun updateScore(tournamentId: String, matchId: String, matchScoreUpdateRequest: MatchScoreUpdateRequest): MatchSimplifiedResponse {
        val tournament: Tournament = findTournamentById(tournamentId)
        if (!isTournamentAlreadyStarted(tournament)) throw TournamentHasNotStartedYetException()

        val match: Match = findMatchById(matchId)
        if (match.isFinished) throw MatchHasNotFinishedYetException()

        //TODO walidacja wyniku, logika
        match.leftTeamScore = matchScoreUpdateRequest.leftTeamScore
        match.rightTeamScore = matchScoreUpdateRequest.rightTeamScore
        match.matchTeamResult =
            getMatchResult(
                tournament.competitionStage,
                matchScoreUpdateRequest.leftTeamScore,
                matchScoreUpdateRequest.rightTeamScore,
                matchScoreUpdateRequest.teamChoice)
        match.isFinished = true

        val updatedMatch: Match = matchRepository.save(match)

        pointsCalculationHelper.calculatePoints(updatedMatch, tournament.competitionStage)

        return match.toSimplifiedResponse()
    }

    fun delete(tournamentId: String, matchId: String) {
        val tournament: Tournament = findTournamentById(tournamentId)
        if (isTournamentAlreadyStarted(tournament)) throw TournamentIsNotInCreationStageException()

        matchRepository.delete(findMatchById(matchId))
    }

    fun findMatchById(matchId: String):
            Match = matchRepository.findById(matchId.toLong()).orElseThrow { MatchDoesNotExistsException() }

    private fun findTeamById(teamId: String):
            Team = teamRepository.findById(teamId.toLong()).orElseThrow { TeamDoesNotExistsException() }

    fun getMatchResult(
        tournamentCompetitionStage: CompetitionStage,
        leftTeamScore: Int,
        rightTeamScore: Int,
        teamChoice: TeamChoice?): TeamChoice {
        var result = TeamChoice.DRAW

        if (leftTeamScore > rightTeamScore) result = TeamChoice.TEAM_LEFT
        if (leftTeamScore < rightTeamScore) result = TeamChoice.TEAM_RIGHT
        if (leftTeamScore == rightTeamScore && tournamentCompetitionStage == CompetitionStage.GROUP_STAGE) result = TeamChoice.DRAW
        if (leftTeamScore == rightTeamScore
            && tournamentCompetitionStage == CompetitionStage.FINALS_STAGE
            && teamChoice != null) result = teamChoice

        return result
    }

    private fun findTournamentById(tournamentId: String):
            Tournament = tournamentRepository.findById(tournamentId.toLong()).orElseThrow { TournamentDoesNotExistsException() }

    private fun findMatchByIdAndTournament(matchId: String, tournament: Tournament):
            Match = matchRepository.findMatchByIdAndTournament(matchId.toLong(), tournament).orElseThrow { MatchDoesNotExistsException() }

    private fun isTournamentAlreadyStarted(tournament: Tournament):
            Boolean = tournament.tournamentStage == TournamentStage.RUNNING_STAGE

    private fun areTwoTeamsTheSame(leftTeamId: String, rightTeamId: String):
            Boolean = leftTeamId == rightTeamId

    private fun areTwoTeamsFromSameGroupInGroupStageMatch(leftTeamGroup: Group, rightTeamGroup: Group, competitionStage: CompetitionStage): Boolean {
//        return competitionStage == CompetitionStage.GROUP_STAGE && leftTeamGroup == rightTeamGroup
        return leftTeamGroup == rightTeamGroup
    }
}