package com.example.demo.member;

public interface MemberRepository {

    Member save(Member member);
    Member findById(Long memberId);
    Member findByNickname(String nickname);
    void delete(Long memberId);
}
