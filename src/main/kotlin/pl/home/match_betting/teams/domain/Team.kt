package pl.home.match_betting.teams.domain

import jakarta.persistence.*
import pl.home.match_betting.groups.domain.Group

@Entity
@Table(name = "teams")
data class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    var id: Long,

    var name: String,

    @ManyToOne
    @JoinColumn(name = "group_id")
    var groupId: Group
)
