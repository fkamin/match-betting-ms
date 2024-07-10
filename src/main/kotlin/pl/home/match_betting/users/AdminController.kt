package pl.home.match_betting.users

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/match-betting/admin")
class AdminController {

    @GetMapping
    fun hello(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello")
    }
}