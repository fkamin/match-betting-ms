package pl.home.match_betting.users

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/match-betting/admin")
class AdminController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello")
    }
}