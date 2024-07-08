package pl.home.match_betting.auths.dto.exceptions

import org.springframework.http.HttpStatus
import pl.home.match_betting.users.dto.exceptions.ApiException

class UserUnauthorizedException: ApiException(HttpStatus.CONFLICT.value(), "User with this login already exists")