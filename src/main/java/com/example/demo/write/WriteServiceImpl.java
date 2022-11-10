package com.example.demo.write;

import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WriteServiceImpl implements WriteService{

    private final MemberRepository memberRepository;

    @Autowired
    public WriteServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public Write createWrite(Long memberId, String title, String content) {
        Member member = memberRepository.findById(memberId);

        return new Write(memberId, title, content);
    }
}
