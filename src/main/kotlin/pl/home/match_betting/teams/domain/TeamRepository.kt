package pl.home.match_betting.teams.domain

import org.springframework.data.jpa.repository.JpaRepository
import pl.home.match_betting.groups.domain.Group
import java.util.Optional

interface TeamRepository: JpaRepository<Team, Long> {

    fun existsByName(name: String): Boolean

    fun findAllByGroup(group: Group): List<Team>

    fun findByIdAndGroup(teamId: Long, group: Group): Optional<Team>
}