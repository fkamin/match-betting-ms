package pl.home.match_betting.tournaments.dto.responses

import pl.home.match_betting.tournaments.domain.Tournament

data class TournamentSimplifiedResponse(
    val id: Long,
    val name: String
)

fun Tournament.toSimplifiedResponse():
        TournamentSimplifiedResponse = TournamentSimplifiedResponse(id, name)