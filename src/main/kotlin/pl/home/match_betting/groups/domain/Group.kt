package pl.home.match_betting.groups.domain

import jakarta.persistence.*
import pl.home.match_betting.teams.domain.Team

@Entity
@Table(name = "groups")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    var id: Long,

    var name: String,

    @OneToMany
    @JoinColumn(name = "team_id")
    var teamList: List<Team> = emptyList()
)
