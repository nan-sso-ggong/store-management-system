package edu.dongguk.cs25server.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetail (
    private val id: Long,
    private var authorities: List<GrantedAuthority>
    ) : UserDetails {

    override fun getAuthorities() = authorities

    override fun getPassword() = null

    override fun getUsername() = id.toString()

    override fun isAccountNonExpired() = false

    override fun isAccountNonLocked() = false

    override fun isCredentialsNonExpired() = false

    override fun isEnabled() = false

    companion object {
        fun create(user: UserLoginForm): CustomUserDetail {
            val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_" + user.getRole()))
            return CustomUserDetail(id = user.getId(), authorities = authorities)
        }
    }
}