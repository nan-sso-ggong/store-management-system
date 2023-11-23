package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.Customer
import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.domain.type.LoginProvider.*
import edu.dongguk.cs25server.domain.type.Membership
import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.dto.response.LoginResponse
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.CustomerRepository
import edu.dongguk.cs25server.security.JwtProvider
import edu.dongguk.cs25server.security.JwtToken
import edu.dongguk.cs25server.util.Oauth2Info
import edu.dongguk.cs25server.util.Oauth2Util
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional

@Service
class AuthService (
    private val customerRepository: CustomerRepository,
    private val oauth2Util: Oauth2Util,
    private val jwtProvider: JwtProvider
) {

    @Transactional
    fun socialLogin(authCode: String, loginProvider: LoginProvider) : LoginResponse {
        var socialId: String? = null
        var socialName: String? = null

        when (loginProvider) {
            KAKAO -> {
                val accessToken = oauth2Util.getKakaoAccessToken(authCode)
                val oauth2info: Oauth2Info  = oauth2Util.getKakaoUserInfo(accessToken);
                socialId = oauth2info.socialId;
                socialName = oauth2info.socialName;
            }
            NAVER -> {
                val accessToken = oauth2Util.getNaverAccessToken(authCode)
                val oauth2info: Oauth2Info  = oauth2Util.getNaverUserInfo(accessToken);
                socialId = oauth2info.socialId;
                socialName = oauth2info.socialName;
            }
            GOOGLE -> {
                val accessToken = oauth2Util.getGoogleAccessToken(authCode)
                val oauth2info: Oauth2Info  = oauth2Util.getGoogleUserInfo(accessToken);
                socialId = oauth2info.socialId;
                socialName = oauth2info.socialName;
            }
        }

        if (socialId == null || socialName == null) { throw GlobalException(ErrorCode.NOT_FOUND_CUSTOMER) }

        val customer: Optional<Customer> = customerRepository.findBySocialIdAndLoginProvider(socialId, loginProvider)
        val loginCustomer = if (customer.isEmpty) {
            customerRepository.save(
                Customer (
                    name = socialName,
                    socialId = socialId,
                    loginProvider = loginProvider,
                    role = UserRole.CUSTOMER,
                    membership = Membership.NORMAL,
                    point = 0,
                    isValid = true,
                    createdAt = LocalDateTime.now()
            ))
        } else {
            customer.get()
        }

        val jwtToken: JwtToken = jwtProvider.createTotalToken(loginCustomer.getId()!!, loginCustomer.role)
        loginCustomer.setLogin(jwtToken.refreshToken)

        return LoginResponse(
            name = loginCustomer.name,
            access_token = jwtToken.accessToken,
            refresh_token = jwtToken.refreshToken
        )
    }

}