package pl.home.match_betting.matches.dtos.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class MatchHasNotFinishedYetException:
        ApiException(HttpStatus.CONFLICT.value(), "Accessed match has not finished yet")