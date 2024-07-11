package pl.home.match_betting.groups.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import pl.home.match_betting.teams.domain.Team

@Entity
@Table(name = "groups")
data class Group(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false, unique = true)
    var name: String = "",

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var teams: List<Team> = emptyList()
)
