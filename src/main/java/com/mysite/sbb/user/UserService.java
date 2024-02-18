package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);

        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //siteUser.setPassword(passwordEncoder.encode(password));
        siteUser.setPassword(password);
        this.userRepository.save(siteUser);

        return siteUser;
    }

    public SiteUser getSiteUser(String username) {
        Optional<SiteUser> optionalSiteUser = this.userRepository.findByusername(username);
        if(optionalSiteUser.isPresent()) {
            return optionalSiteUser.get();
        } else {
            return null;
        }
    }

    public boolean isLoginSuccess(String username, String password) {
        SiteUser siteUser = getSiteUser(username);
        if (siteUser != null) {
            String siteUserPassword = siteUser.getPassword();
            if (siteUserPassword.equals(password))
                return true;
            else
                return false;
        } else {
            return false;
        }
    }
}
