package pl.home.match_betting.tournaments.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class TournamentHasNotStartedYetException:
        ApiException(HttpStatus.CONFLICT.value(), "Accessed tournament has not started yet")