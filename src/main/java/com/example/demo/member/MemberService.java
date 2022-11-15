package com.example.demo.member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member join(Member member);   //회원가입

    List<Member> findAllMember();   //회원 전체 조회

    Optional<Member> findOneMember(Long memberId);  //회원 검색

    void deleteMember(Long memberId);   //회원 삭제
}
