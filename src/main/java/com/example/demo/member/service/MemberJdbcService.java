package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


public class MemberJdbcService implements MemberService{

    private final MemberJdbcRepository memberJdbcRepository;
    public MemberJdbcService(MemberJdbcRepository memberJdbcRepository) {
        this.memberJdbcRepository = memberJdbcRepository;
    }

    @Override
    public Member join(Member member) {
        duplicateNicknameCheck(member);

        return memberJdbcRepository.save(member);
    }

    //중복 닉네임 검사
    private void duplicateNicknameCheck(Member member){
        memberJdbcRepository.duplicateNicknameCheck(member.getNickname())
                .ifPresent(m -> {   //ifPresent -> Optional 안에 null이 아닌 값이 있으면 동작
                    throw new IllegalStateException("이미 존재하는 닉네임입니다");
                });
    }


    @Override
    public List<Member> findAllMember() {
        return memberJdbcRepository.findAll();
    }

    @Override
    public Optional<Member> findOneMember(Long memberId) {

        Optional<Member> byId = memberJdbcRepository.findById(memberId);
        if(byId.isEmpty()){
            throw new NoSuchElementException("값이 없다");
        }
        return byId;
    }

    @Override
    public void deleteMember(Long memberId) {
        memberJdbcRepository.delete(memberId);
    }
}
