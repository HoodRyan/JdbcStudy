package com.example.demo.write.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.write.entity.Write;

import java.util.List;
import java.util.Optional;

public interface WriteRepository {

    Write save(Write write);
    Optional<Write> findById(Long writeId);
    List<Write> findAll();
    void update(Long writeId, Write write);
    void delete(Long writeId);
    void clear();


}
