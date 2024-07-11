package pl.home.match_betting.matches.domain

import org.springframework.data.jpa.repository.JpaRepository
import pl.home.match_betting.tournaments.domain.Tournament
import java.util.Optional

interface MatchRepository: JpaRepository<Match, Long> {

    fun findAllByTournament(tournament: Tournament): List<Match>

    fun findMatchByIdAndTournament(matchId: Long, tournament: Tournament): Optional<Match>
}