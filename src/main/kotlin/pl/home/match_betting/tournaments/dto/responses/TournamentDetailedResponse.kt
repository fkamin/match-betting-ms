package pl.home.match_betting.tournaments.dto.responses

import pl.home.match_betting.matches.domain.CompetitionStage
import pl.home.match_betting.matches.domain.Match
import pl.home.match_betting.tournaments.domain.Tournament
import pl.home.match_betting.tournaments.domain.TournamentStage

data class TournamentDetailedResponse(
    val id: Long,
    val name: String,
    val competitionStage: CompetitionStage,
    val tournamentStage: TournamentStage,
    val matches: List<Match>
)

fun Tournament.toDetailedResponse():
        TournamentDetailedResponse = TournamentDetailedResponse(id, name, competitionStage, tournamentStage, matches)