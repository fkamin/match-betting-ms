package pl.home.match_betting.users.dto.exceptions

import org.springframework.http.HttpStatus

class UserAlreadyExistsException :
        ApiException(HttpStatus.CONFLICT.value(), "User with this login already exists")