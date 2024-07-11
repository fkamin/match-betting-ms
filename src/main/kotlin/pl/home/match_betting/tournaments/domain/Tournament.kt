package pl.home.match_betting.tournaments.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import pl.home.match_betting.matches.domain.CompetitionStage
import pl.home.match_betting.matches.domain.Match

@Entity
@Table(name = "tournaments")
data class Tournament(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var competitionStage: CompetitionStage,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var tournamentStage: TournamentStage = TournamentStage.CREATION_STAGE,

    @JsonIgnore
    @OneToMany(mappedBy = "tournament", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var matches: List<Match> = mutableListOf()
) {
    fun bettingPhase(): Unit = updateTournamentStage(tournamentStage.betting(tournament = this))

    fun runningPhase(): Unit = updateTournamentStage(tournamentStage.running(tournament = this))

    private fun updateTournamentStage(tournamentStage: TournamentStage) {
        this.tournamentStage = tournamentStage
    }
}