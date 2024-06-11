package pl.home.match_betting.users.domain

import org.springframework.data.repository.Repository

interface UserRepository : Repository<User, Long> {

}