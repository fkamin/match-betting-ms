package pl.home.match_betting.bets.domain

import jakarta.persistence.*
import pl.home.match_betting.matches.domain.Match
import pl.home.match_betting.users.domain.User

@Entity
@Table(name = "bets")
data class Bet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    var match: Match,

    @Column(nullable = false)
    var predictedLeftTeamScore: Int,

    @Column(nullable = false)
    var predictedRightTeamScore: Int,

    @Column(nullable = false)
    var isFinalStage: Boolean,

    @Enumerated(EnumType.STRING)
    @Column
    var predictedWinner: TeamChoice
)
