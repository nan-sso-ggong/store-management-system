package edu.dongguk.cs25server.security

import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.domain.type.UserRole.*
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.CustomerRepository
import edu.dongguk.cs25server.repository.HeadquartersRepository
import edu.dongguk.cs25server.repository.ManagerRepository
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*

@Component
class JwtProvider (
    private val customerRepository: CustomerRepository,

    private val managerRepository: ManagerRepository,

    private val headquartersRepository: HeadquartersRepository,

    @Value("\${jwt.secret}")
    private val secretKey: String,

    @Value("\${jwt.accessExpiredMs}")
    private var accessExpiredMs: Long,

    @Value("\${jwt.refreshExpiredMs}")
    private val refreshExpiredMs: Long,

) : InitializingBean {

    private var key: Key? = null

    override fun afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    fun createToken(id: Long, role: UserRole, isAccess: Boolean) : String {
        val claims = Jwts.claims()
        claims["id"] = id
        if (isAccess) {
            claims["role"] = role
        }

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + if (isAccess) {accessExpiredMs.toInt()} else {refreshExpiredMs.toInt()}))
            .signWith(key , SignatureAlgorithm.HS256)
            .compact()
    }

    fun createTotalToken(id: Long, userRole: UserRole) = JwtToken(
        accessToken = createToken(id, userRole, true),
        refreshToken = createToken(id, userRole, false))


    fun reissueToken(request: HttpServletRequest, role: UserRole): String {
        val refreshToken = refineToken(request)
        val userId = getUserId(refreshToken)

        val user: UserLoginForm = when (role) {
            CUSTOMER -> customerRepository.findByIdAndRefreshToken(userId, refreshToken)
//            MANAGER -> managerRepository.findByIdAndRefreshToken(userId, refreshToken)
//            HQ -> headquartersRepository.findByIdAndRefreshToken(userId, refreshToken)
            else -> throw GlobalException(ErrorCode.NOT_FOUND_ERROR)
        } ?: throw GlobalException(ErrorCode.NOT_FOUND_ERROR)

        return createToken(user.getId(), user.getRole(), true)
    }

    @Throws(JwtException::class)
    fun getUserId(token: String) = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .body["id"].toString().toLong()

    @Throws(JwtException::class)
    fun validateToken(token: String) : Claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .body

    companion object {
        @Throws(JwtException::class)
        fun refineToken(request: HttpServletRequest): String {
            val beforeToken = request.getHeader("Authorization")
            var afterToken = if (StringUtils.hasText(beforeToken) && beforeToken.startsWith("Bearer ")) {
                beforeToken.substring(7)
            } else {
                throw GlobalException(ErrorCode.TOKEN_INVALID_ERROR)
            }
            return afterToken
        }
    }




}