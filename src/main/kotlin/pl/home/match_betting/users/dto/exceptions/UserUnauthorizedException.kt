package pl.home.match_betting.users.dto.exceptions

import org.springframework.http.HttpStatus

class UserUnauthorizedException :
        ApiException(HttpStatus.UNAUTHORIZED.value(), "Error occurred during the login process")