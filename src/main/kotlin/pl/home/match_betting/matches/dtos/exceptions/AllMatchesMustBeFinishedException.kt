package pl.home.match_betting.matches.dtos.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class AllMatchesMustBeFinishedException:
    ApiException(HttpStatus.CONFLICT.value(), "All matches must be finished before deletion")