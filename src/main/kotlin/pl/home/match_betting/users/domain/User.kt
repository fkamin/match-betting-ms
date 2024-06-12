package pl.home.match_betting.users.domain

import jakarta.persistence.*
import pl.home.match_betting.bets.domain.Bet

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0L,

    var name: String,
    var login: String,
    var password: String,

    @Column(name = "is_admin")
    var isAdmin: Boolean = false,

    @Column(name = "total_points")
    var totalPoints: Int = 0,

    @OneToMany
    @JoinColumn(name = "bet_id")
    var betList: List<Bet> = emptyList(),
)