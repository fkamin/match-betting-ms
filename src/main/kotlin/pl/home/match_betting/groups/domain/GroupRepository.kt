package pl.home.match_betting.groups.domain

import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, Long> {
    fun existsByName(name: String): Boolean
}