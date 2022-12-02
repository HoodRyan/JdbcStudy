package com.example.demo.login;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import com.example.demo.member.service.MemberJdbcServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberJdbcRepositoryV2 memberJdbcRepositoryV2;

    public Member login(String nickname){
        return memberJdbcRepositoryV2.duplicateNicknameCheck(nickname)
                .filter(member -> member.getNickname().equals(nickname))
                .orElse(null);
    }



}
