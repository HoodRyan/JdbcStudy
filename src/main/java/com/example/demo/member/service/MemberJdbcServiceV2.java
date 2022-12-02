package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberJdbcRepositoryV2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


public class MemberJdbcServiceV2 implements MemberService{

    private final MemberJdbcRepositoryV2 memberJdbcRepository;
    public MemberJdbcServiceV2(MemberJdbcRepositoryV2 memberJdbcRepository) {
        this.memberJdbcRepository = memberJdbcRepository;
    }

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Override
    public Member join(Member member) {
        duplicateNicknameCheck(member);

        return memberJdbcRepository.save(member);
    }

    /**
     * 중복 닉네임 검사
     * @param member
     */
    private void duplicateNicknameCheck(Member member){
        memberJdbcRepository.duplicateNicknameCheck(member.getNickname())
                .ifPresent(m -> {   //ifPresent -> Optional 안에 null이 아닌 값이 있으면 동작
                    throw new IllegalStateException("이미 존재하는 닉네임입니다");
                });
    }

    /**
     * 전체 멤버 조회
     * @return
     */
    @Override
    public List<Member> findAllMember() {
        return memberJdbcRepository.findAll();
    }

    /**
     * 멤버 정보 조회
     * @param memberId
     * @return
     */
    @Override
    public Optional<Member> findOneMember(Long memberId) {

        Optional<Member> byId = memberJdbcRepository.findById(memberId);
        if(byId.isEmpty()){
            throw new NoSuchElementException("값이 없다");
        }
        return byId;
    }

    /**
     * 회원 탈퇴
     * @param memberId
     */
    @Override
    public void deleteMember(Long memberId) {
        memberJdbcRepository.delete(memberId);
    }
}
