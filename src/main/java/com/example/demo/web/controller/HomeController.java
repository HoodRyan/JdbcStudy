package com.example.demo.web.controller;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import com.example.demo.member.service.MemberJdbcService;
import com.example.demo.member.service.MemberJdbcServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberJdbcServiceV2 memberJdbcServiceV2;
    private final MemberJdbcRepositoryV2 memberJdbcRepositoryV2;

//    @GetMapping("/")
//    public String home(){
//        return "home";
//    }

    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "nickname",required = false) String nickname, Model model){
        if(nickname == null){
            return "home";
        }

        //로그인
        Optional<Member> loginMember = memberJdbcRepositoryV2.duplicateNicknameCheck(nickname);
        if(loginMember.isEmpty()){
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
}
