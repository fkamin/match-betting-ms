package pl.home.match_betting.users.dto.exceptions

import org.springframework.http.HttpStatus

class UserNotFoundException :
    ApiException(HttpStatus.NOT_FOUND.value(), "Accessed user does not exist")