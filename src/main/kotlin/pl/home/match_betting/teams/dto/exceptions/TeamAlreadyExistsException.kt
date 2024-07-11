package pl.home.match_betting.teams.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class TeamAlreadyExistsException:
    ApiException(HttpStatus.CONFLICT.value(), "Team with this name already exists")