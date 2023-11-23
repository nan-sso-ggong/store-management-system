package edu.dongguk.cs25server.security

import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.CustomerRepository
import edu.dongguk.cs25server.util.Log.Companion.log
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class CustomUserDetailService (
    private val customerRepository: CustomerRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val roles: MutableCollection<SimpleGrantedAuthority> = ArrayList()
        roles.add(SimpleGrantedAuthority("ROLE_USER"))

        val user: UserLoginForm = customerRepository.findByIdAndRefreshToken(username.toLong())
            ?: throw GlobalException(ErrorCode.ACCESS_DENIED_ERROR)

        return CustomUserDetail.create(user)
    }

    fun loadUserByUsernameAndUserRole(username: String, role: String): UserDetails {
        val roles: MutableCollection<SimpleGrantedAuthority> = ArrayList()
        roles.add(SimpleGrantedAuthority("ROLE_$role"))

        // TODO 점주, 본사 로그인 후 구현
//        val user: UserLoginForm = when (role) {
//            "ROLE_CUSTOMER" -> {
//                customerRepository.findByIdAndRefreshToken(username.toLong())
//                    ?: throw GlobalException(ErrorCode.ACCESS_DENIED_ERROR)
//            }
//            "ROLE_MANAGER" -> {
//                managerRepository.findByIdAndRefreshToken(username.toLong())
//            }
//            "ROLE_HQ" -> {
//                headquartersRepository.findByIdAndRefreshToken(username.toLong())
//            }
//        }

        val user: UserLoginForm = customerRepository.findByIdAndRefreshToken(username.toLong())
            ?: throw GlobalException(ErrorCode.ACCESS_DENIED_ERROR)

        return CustomUserDetail.create(user)
    }
}

