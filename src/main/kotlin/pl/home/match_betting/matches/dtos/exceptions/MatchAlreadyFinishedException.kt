package pl.home.match_betting.matches.dtos.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class MatchAlreadyFinishedException:
        ApiException(HttpStatus.CONFLICT.value(), "Accessed match already finished")