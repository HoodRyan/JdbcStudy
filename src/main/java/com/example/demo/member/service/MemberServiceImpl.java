package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member join(Member member) {
        duplicateNicknameCheck(member);

        return memberRepository.save(member);
    }

    //중복 닉네임 검사
    private void duplicateNicknameCheck(Member member){
        memberRepository.findByNickname(member.getNickname())
                .ifPresent(m -> {   //ifPresent -> Optional 안에 null이 아닌 값이 있으면 동작
                    throw new IllegalStateException("이미 존재하는 닉네임입니다");
                });
    }

    @Override
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findOneMember(Long memberId) {
        Optional<Member> byId = memberRepository.findById(memberId);
        if(byId.isEmpty()){
            throw new NoSuchElementException("값이 없다");
        }
        return byId;
    }

    @Override
    public void deleteMember(Long memberId) {
        memberRepository.delete(memberId);
    }


}
