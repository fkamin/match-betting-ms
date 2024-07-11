package pl.home.match_betting.tournaments.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TournamentRepository: JpaRepository<Tournament, Long> {

}