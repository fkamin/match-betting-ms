package pl.home.match_betting.matches.dtos.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class TeamsInMatchCanNotBeTheSameException:
        ApiException(HttpStatus.CONFLICT.value(), "Teams in match can not be the same")