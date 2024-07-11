package pl.home.match_betting.tournaments.dto.requests

import pl.home.match_betting.matches.domain.CompetitionStage

data class TournamentUpdateRequest(
    val name: String,
    val competitionStage: CompetitionStage
)
