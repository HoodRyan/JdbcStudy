package com.example.demo.write;

import com.example.demo.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface WriteService {
    Write create(Write write, Member member);   //게시글 작성
    List<Write> findAllWrite();    //게시글 전체 조회
    Optional<Write> findOneWrite(Long writeId); //게시글 상세 조회
    void update(Long writeId, Write write); //게시글 수정
    void delete(Long writeId);  //게시글 삭제


}
