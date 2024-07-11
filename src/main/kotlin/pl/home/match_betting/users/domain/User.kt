package pl.home.match_betting.users.domain

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import pl.home.match_betting.bets.domain.Bet

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var login: String,

    @Column(nullable = false, name = "password")
    var encodedPassword: String,

    @Column(nullable = false) @Enumerated(EnumType.STRING)
    var role: Role,

    @Column(name = "total_points")
    var totalPoints: Int = 0,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var bets: List<Bet> = emptyList()

) : UserDetails {

    override fun getAuthorities():
            MutableCollection<out GrantedAuthority> = arrayListOf(SimpleGrantedAuthority("ROLE_${role.name}"))

    override fun getUsername(): String = login

    override fun getPassword(): String = encodedPassword

}