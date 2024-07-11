package pl.home.match_betting.teams.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class TeamDoesNotExistsException:
    ApiException(HttpStatus.NOT_FOUND.value(), "Accessed team does not exists")