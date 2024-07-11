package pl.home.match_betting.tournaments.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class TournamentDoesNotExistsException:
    ApiException(HttpStatus.NOT_FOUND.value(), "Accessed tournament does not exists")