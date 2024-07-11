package pl.home.match_betting.matches.dtos.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class MatchDoesNotExistsException:
    ApiException(HttpStatus.NOT_FOUND.value(), "Accessed match does not exists")