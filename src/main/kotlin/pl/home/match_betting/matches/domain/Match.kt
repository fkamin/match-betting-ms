package pl.home.match_betting.matches.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import pl.home.match_betting.bets.domain.Bet
import pl.home.match_betting.teams.domain.Team
import pl.home.match_betting.tournaments.domain.Tournament
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
data class Match(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore
    @JoinColumn(name = "tournament_id", nullable = false)
    val tournament: Tournament,

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore
    @JoinColumn(name = "team_left_id", nullable = false)
    var leftTeam: Team,

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore
    @JoinColumn(name = "team_right_id", nullable = false)
    var rightTeam: Team,

    @Column
    var leftTeamScore: Int? = null,

    @Column
    var rightTeamScore: Int? = null,

    @Column
    @Enumerated(EnumType.STRING)
    var matchTeamResult: TeamChoice? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "match", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var bets: MutableList<Bet> = mutableListOf(),

    @Column(nullable = false)
    var date: LocalDateTime,

    @Column(nullable = false)
    var isFinished: Boolean = false
)
