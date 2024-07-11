package pl.home.match_betting.tournaments.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class TournamentIsNotInBettingStageException:
        ApiException(HttpStatus.CONFLICT.value(), "Accessed tournament is not in betting stage")