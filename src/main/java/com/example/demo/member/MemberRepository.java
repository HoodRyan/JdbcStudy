package com.example.demo.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByNickname(String nickname);
    List<Member> findAll();
    void delete(Long memberId);
}
