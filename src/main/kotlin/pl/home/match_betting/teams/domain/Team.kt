package pl.home.match_betting.teams.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import pl.home.match_betting.groups.domain.Group

@Entity
@Table(name = "teams")
data class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    var id: Long = 0L,

    var name: String = "",

    @JsonBackReference
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "group_id")
    var group: Group
)
