package pl.home.match_betting.matches.domain

import jakarta.persistence.*
import pl.home.match_betting.teams.domain.Team
import java.util.Date

@Entity
@Table(name = "matches")
data class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    var id: Long,

    @ManyToOne
    @JoinColumn(name = "team_left_id")
    var teamLeft: Team,

    @ManyToOne
    @JoinColumn(name = "team_right_id")
    var teamRight: Team,

    var finalScoreLeft: String,
    var finalScoreRight: String,
    var date: Date,
    var isFinished: Boolean = false
)
