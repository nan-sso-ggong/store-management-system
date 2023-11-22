package edu.dongguk.cs25backend.security;

import edu.dongguk.cs25backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService extends DefaultOAuth2UserService {

    private final CustomerRepository customerRepository;

//    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserLoginForm user = customerRepository.findByIdAndRefreshToken(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException("ACCESS_DENIED_ERROR"));

        return CustomUserDetail.create(user);
    }

    public UserDetails loadUserByUsernameAndUserRole(String username, String userRole) throws UsernameNotFoundException {
        Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(userRole));

        switch (userRole) {
            case "ROLE_CUSTOMER" -> { log.info("CUSTOMER"); }
            case "ROLE_MANAGER" -> { log.info("MANAGER"); }
            case "ROLE_HQ" -> { log.info("HQ"); }

        }

        UserLoginForm user = customerRepository.findByIdAndRefreshToken(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException("ACCESS_DENIED_ERROR"));

        return CustomUserDetail.create(user);
    }
}
