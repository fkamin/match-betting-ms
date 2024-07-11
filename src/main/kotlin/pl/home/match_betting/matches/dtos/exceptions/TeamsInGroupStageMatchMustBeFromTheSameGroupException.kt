package pl.home.match_betting.matches.dtos.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class TeamsInGroupStageMatchMustBeFromTheSameGroupException:
        ApiException(HttpStatus.CONFLICT.value(), "Teams in group stage match must be from the same group")