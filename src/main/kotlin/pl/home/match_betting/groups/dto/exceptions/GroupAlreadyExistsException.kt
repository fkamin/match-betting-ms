package pl.home.match_betting.groups.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class GroupAlreadyExistsException: ApiException(HttpStatus.CONFLICT.value(), "Group with this name already exists")