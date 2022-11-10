package com.example.demo.write;

public interface WriteService {
    Write createWrite(Long memberId, String title, String content);
}
