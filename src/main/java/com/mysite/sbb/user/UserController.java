package com.mysite.sbb.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {       // 중복된 사용자명 가입 처리
            e.printStackTrace();
            bindingResult.reject("signfailed", "이미 등록된 사용자입니다. 다른 사용자명을 입력해 주세요.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupfailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

//    @PostMapping("/login")
//    public String login(@Valid UserLoginForm userLoginForm, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "login_form";
//        }
//
//        boolean isLoginSuccess
//                = this.userService.isLoginSuccess(
//                        userLoginForm.getUsername(),
//                        userLoginForm.getPassword()
//                    );
//        if (isLoginSuccess) {
//            return "redirect:/";
//        } else {
//            bindingResult.reject("usernamePasswordInCorrect","로그인 오류");
//            return "login_form";
//        }
//    }
}
