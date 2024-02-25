package com.mysite.sbb.user;

import com.mysite.sbb.DataNotFoundException;
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

        // 회원가입시 패스워드 암호화 처리
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        siteUser.setPassword(passwordEncoder.encode(password));
        //siteUser.setPassword(password);
        this.userRepository.save(siteUser);

        return siteUser;
    }

    public SiteUser getUser(String username) {
        // username에 해당하는 사용자 정보 가져오기
        Optional<SiteUser> optionalSiteUser = this.userRepository.findByusername(username);
        // username이 있으면 실제 siteuser 사용자 정보 전달. 없으면 null을 전달
        if(optionalSiteUser.isPresent()) {
            return optionalSiteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public boolean isLoginSuccess(String username, String password) {
        SiteUser siteUser = getUser(username);
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
