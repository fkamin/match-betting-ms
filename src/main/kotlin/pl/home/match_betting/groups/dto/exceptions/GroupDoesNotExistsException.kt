package pl.home.match_betting.groups.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class GroupDoesNotExistsException:
    ApiException(HttpStatus.NOT_FOUND.value(), "Accessed group does not exists")