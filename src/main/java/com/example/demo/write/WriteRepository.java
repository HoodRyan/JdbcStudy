package com.example.demo.write;

import com.example.demo.member.Member;

import java.util.List;

public interface WriteRepository {

    Write save(Write write);    //게시글 저장
    Write findById(Long writeId);   //게시글 상세 정보 조회
    List<Write> findAll();  //게시글 전체 조회
    void update(Long writeId, Write write); //게시글 수정
    void delete(Long writeId);  //게시글 삭제



}
