package pl.home.match_betting.bets.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import pl.home.match_betting.matches.domain.Match
import pl.home.match_betting.matches.domain.TeamChoice
import pl.home.match_betting.users.domain.User

@Entity
@Table(name = "bets")
data class Bet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore
    @JoinColumn(name = "match_id", nullable = false)
    var match: Match,

    @Column(nullable = false)
    var leftTeamScorePrediction: Int,

    @Column(nullable = false)
    var rightTeamScorePrediction: Int,

    @Column
    @Enumerated(EnumType.STRING)
    var winnerPrediction: TeamChoice
)
