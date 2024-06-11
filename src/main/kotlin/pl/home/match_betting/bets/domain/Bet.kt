package pl.home.match_betting.bets.domain

import jakarta.persistence.*
import pl.home.match_betting.matches.domain.Match
import pl.home.match_betting.users.domain.User

@Entity
@Table(name = "bets")
data class Bet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_id")
    var id: Long,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var userId: User,

    @ManyToOne
    @JoinColumn(name = "match_id")
    var matchId: Match,

    var scoreLeft: String,
    var scoreRight: String
)
