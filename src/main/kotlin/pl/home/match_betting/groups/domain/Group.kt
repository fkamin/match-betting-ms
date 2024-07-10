package pl.home.match_betting.groups.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import pl.home.match_betting.teams.domain.Team

@Entity
@Table(name = "groups")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    var id: Long = 0L,

    var name: String = "",

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = [CascadeType.ALL])
    var teams: List<Team> = emptyList()
)
