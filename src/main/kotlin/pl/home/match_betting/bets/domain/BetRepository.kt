package pl.home.match_betting.bets.domain

import org.springframework.data.jpa.repository.JpaRepository
import pl.home.match_betting.matches.domain.Match
import pl.home.match_betting.users.domain.User
import java.util.*

interface BetRepository: JpaRepository<Bet, Long> {
    fun findAllByUser(user: User): List<Bet>

    fun findAllByMatch(match: Match): List<Bet>

    fun findBetByUserAndMatch(user: User, match: Match): Optional<Bet>
}