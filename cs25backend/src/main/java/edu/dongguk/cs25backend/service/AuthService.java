package edu.dongguk.cs25backend.service;

import edu.dongguk.cs25backend.domain.Customer;
import edu.dongguk.cs25backend.domain.type.LoginProvider;
import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.exception.ErrorCode;
import edu.dongguk.cs25backend.repository.CustomerRepository;
import edu.dongguk.cs25backend.response.LoginResponse;
import edu.dongguk.cs25backend.security.JwtProvider;
import edu.dongguk.cs25backend.security.JwtToken;
import edu.dongguk.cs25backend.util.Oauth2CustomerInfo;
import edu.dongguk.cs25backend.util.Oauth2Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomerRepository customerRepository;
    private final Oauth2Util oauth2Util;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponse login(String authCode, LoginProvider loginProvider) {
        String accessToken = null;
        String socialId = null;
        String socialName = null;

        switch (loginProvider) {
            case KAKAO -> {
                accessToken = oauth2Util.getKakaoAccessToken(authCode);
                Oauth2CustomerInfo oauth2CustomerInfo = oauth2Util.getKakaoUserInfo(accessToken);
                socialId = oauth2CustomerInfo.getSocialId();
                socialName = oauth2CustomerInfo.getSocialName();
            }
            case NAVER -> {
                accessToken = oauth2Util.getNaverAccessToken(authCode);
                Oauth2CustomerInfo oauth2CustomerInfo = oauth2Util.getNaverUserInfo(accessToken);
                socialId = oauth2CustomerInfo.getSocialId();
                socialName = oauth2CustomerInfo.getSocialName();
            }
            case GOOGLE -> {
                accessToken = oauth2Util.getGoogleAccessToken(authCode);
                Oauth2CustomerInfo oauth2CustomerInfo = oauth2Util.getGoogleUserInfo(accessToken);
                socialId = oauth2CustomerInfo.getSocialId();
                socialName = oauth2CustomerInfo.getSocialName();
            }
        }
        if (socialId == null) { throw new CS25Exception(ErrorCode.NOT_FOUND_ERROR); }

        Optional<Customer> customer = customerRepository.findBySocialIdAndProvider(socialId, loginProvider);
        Customer loginCustomer = customer.isEmpty()
                ? customerRepository.save(Customer.builder()
                .name(socialName)
                .socialId(socialId)
                .provider(loginProvider)
                .build())
                : customer.get();

        JwtToken jwtToken  = jwtProvider.createTotalToken(loginCustomer.getId(), loginCustomer.getRole());
        loginCustomer.setRefreshToken(jwtToken.getRefreshToken());
        loginCustomer.setIsLogin(true);

        return LoginResponse.builder()
                .name(loginCustomer.getName())
                .access_token(jwtToken.getAccessToken())
                .refresh_token(jwtToken.getRefreshToken())
                .build();
    }
}
