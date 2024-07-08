package pl.home.match_betting.users.domain

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pl.home.match_betting.bets.domain.Bet

@Entity
@Table(name = "users")
data class User(
    var name: String,
    var login: String,

    @Column(name = "password")
    var encodedPassword: String,

    @Enumerated(EnumType.STRING)
    var role: Role
) : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long = 0L

    @Column(name = "total_points")
    var totalPoints: Int = 0

    @OneToMany
    @JoinColumn(name = "bet_id")
    var betList: List<Bet> = emptyList()
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return arrayListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getUsername(): String {
        return name
    }

    override fun getPassword(): String {
        return encodedPassword
    }

}