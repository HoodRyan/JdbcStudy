package com.example.demo.write;

import com.example.demo.member.Member;

import java.util.List;
import java.util.Optional;

public interface WriteRepository {

    Write save(Write write, Member member);
    Optional<Write> findById(Long writeId);
    List<Write> findAll();
    void update(Long writeId, Write write);
    void delete(Long writeId);



}
