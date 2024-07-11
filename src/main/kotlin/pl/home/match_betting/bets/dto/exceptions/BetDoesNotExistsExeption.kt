package pl.home.match_betting.bets.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class BetDoesNotExistsExeption:
        ApiException(HttpStatus.NOT_FOUND.value(), "Accessed bet does not exists")