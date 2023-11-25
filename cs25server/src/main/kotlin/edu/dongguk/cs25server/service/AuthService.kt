package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.Customer
import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.domain.type.AllowStatus
import edu.dongguk.cs25server.domain.type.LoginProvider
import edu.dongguk.cs25server.domain.type.LoginProvider.*
import edu.dongguk.cs25server.domain.type.UserRole
import edu.dongguk.cs25server.domain.type.UserRole.*
import edu.dongguk.cs25server.dto.request.JoinRequest
import edu.dongguk.cs25server.dto.request.JoinRequestForManager
import edu.dongguk.cs25server.dto.request.LoginRequest
import edu.dongguk.cs25server.dto.response.LoginResponse
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.CustomerRepository
import edu.dongguk.cs25server.repository.HeadquartersRepository
import edu.dongguk.cs25server.repository.ManagerRepository
import edu.dongguk.cs25server.repository.StoreRepository
import edu.dongguk.cs25server.security.JwtProvider
import edu.dongguk.cs25server.security.JwtToken
import edu.dongguk.cs25server.util.Oauth2Info
import edu.dongguk.cs25server.util.Oauth2Util
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthService(
    private val customerRepository: CustomerRepository,
    private val headquartersRepository: HeadquartersRepository,
    private val managerRepository: ManagerRepository,
    private val storeRepository: StoreRepository,
    private val oauth2Util: Oauth2Util,
    private val jwtProvider: JwtProvider,
) {

    fun socialLogin(authCode: String, loginProvider: LoginProvider): LoginResponse {
        var socialId: String? = null
        var socialName: String? = null

        when (loginProvider) {
            KAKAO -> {
                val accessToken = oauth2Util.getKakaoAccessToken(authCode)
                val oauth2info: Oauth2Info = oauth2Util.getKakaoUserInfo(accessToken)
                socialId = oauth2info.socialId
                socialName = oauth2info.socialName
            }

            NAVER -> {
                val accessToken = oauth2Util.getNaverAccessToken(authCode)
                val oauth2info: Oauth2Info = oauth2Util.getNaverUserInfo(accessToken)
                socialId = oauth2info.socialId
                socialName = oauth2info.socialName
            }

            GOOGLE -> {
                val accessToken = oauth2Util.getGoogleAccessToken(authCode)
                val oauth2info: Oauth2Info = oauth2Util.getGoogleUserInfo(accessToken)
                socialId = oauth2info.socialId
                socialName = oauth2info.socialName
            }
        }

        val loginCustomer = customerRepository.findBySocialIdAndLoginProvider(socialId, loginProvider)
            ?: customerRepository.save(
                Customer(
                    name = socialName,
                    socialId = socialId,
                    loginProvider = loginProvider
                )
            )

        val jwtToken: JwtToken = jwtProvider.createTotalToken(loginCustomer.getId()!!, loginCustomer.role)
        loginCustomer.setLogin(jwtToken.refreshToken)

        return LoginResponse(
            name = loginCustomer.name,
            access_token = jwtToken.accessToken,
            refresh_token = jwtToken.refreshToken
        )
    }

    fun localJoin(joinRequest: JoinRequest, role: UserRole): Boolean {
        when (role) {
            CUSTOMER -> return false
            MANAGER -> return false
            HQ -> headquartersRepository.save(joinRequest.toHeadquarters())
        }
        return true
    }

    fun localJoinForManager(joinRequest: JoinRequestForManager, role: UserRole): Boolean {
        when (role) {
            CUSTOMER -> return false
            MANAGER -> {
                val manager: Manager = joinRequest.toManager()
                managerRepository.save(manager)
                storeRepository.save(joinRequest.toStore(manager))
            }
            HQ -> return false
        }
        return true
    }

    fun managerLogin(loginRequest: LoginRequest): LoginResponse {
        val loginManager = (managerRepository.findByLoginIdAndPassword(loginRequest.login_id, loginRequest.password)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_MANAGER))

        if (loginManager.status != AllowStatus.APPROVAL) {
            throw GlobalException(ErrorCode.MANAGER_NOT_ALLOW)
        }

        val jwtToken: JwtToken = jwtProvider.createTotalToken(loginManager.getId()!!, loginManager.role)
        loginManager.setLogin(jwtToken.refreshToken)

        return LoginResponse(
            name = loginManager.name,
            access_token = jwtToken.accessToken,
            refresh_token = jwtToken.refreshToken
        )
    }

    fun headquartersLogin(loginRequest: LoginRequest): LoginResponse {
        val loginHeadquarters = (headquartersRepository.findByLoginIdAndPassword(loginRequest.login_id, loginRequest.password)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_HQ))
        val jwtToken: JwtToken = jwtProvider.createTotalToken(loginHeadquarters.getId()!!, loginHeadquarters.getRole())
        loginHeadquarters.setLogin(jwtToken.refreshToken)

        return LoginResponse(
            name = loginHeadquarters.getName(),
            access_token = jwtToken.accessToken,
            refresh_token = jwtToken.refreshToken
        )
    }

    fun logout(id: Long, role: UserRole): Boolean {
        when (role) {
            CUSTOMER -> {
                val customer = customerRepository.findByIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(id)
                    ?: throw GlobalException(ErrorCode.NOT_FOUND_CUSTOMER)
                customer.setLogout()
            }

            MANAGER -> {
                val manager = managerRepository.findByIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(id)
                    ?: throw GlobalException(ErrorCode.NOT_FOUND_MANAGER)
                manager.setLogout()
            }

            HQ -> {
                val headquarters = headquartersRepository.findByIdAndRefreshTokenIsNotNullAndIsLoginIsTrue(id)
                    ?: throw GlobalException(ErrorCode.NOT_FOUND_HQ)
                headquarters.setLogout()
            }
        }
        return true
    }

}


