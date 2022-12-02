package com.example.demo.web.controller.member;

import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberJdbcServiceV2;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberJdbcServiceV2 memberJdbcServiceV2;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("member") Member member){
        return "members/addMemberForm";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/addMemberForm";
        }
        memberJdbcServiceV2.join(member);
        return "redirect:/";
    }

}
