package pl.home.match_betting.matches.domain

import jakarta.persistence.*
import pl.home.match_betting.bets.domain.Bet
import pl.home.match_betting.teams.domain.Team
import java.time.LocalDateTime

@Entity
@Table(name = "matches")
data class Match(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_left_id", nullable = false)
    var teamLeft: Team,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_right_id", nullable = false)
    var teamRight: Team,

    @Column
    var teamLeftScore: Int,

    @Column
    var teamRightScore: Int,

    @Column(nullable = false)
    var date: LocalDateTime,

    @OneToMany(mappedBy = "match", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var bets: List<Bet> = emptyList(),

    var isFinished: Boolean = false
)
