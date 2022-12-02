package com.example.demo.web.controller.login;

import com.example.demo.login.LoginService;
import com.example.demo.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.http.HttpResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member login = loginService.login(form.getNickname());
        log.info("login? {}",login);
        if(login == null){
            bindingResult.reject("loginFail","없는 닉네임 입니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리
        Cookie idCookie = new Cookie("nickname",
                String.valueOf(login.getNickname()));
        response.addCookie(idCookie);

        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "nickname");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
