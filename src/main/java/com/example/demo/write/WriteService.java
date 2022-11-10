package com.example.demo.write;

public interface WriteService {
    void create(Write write);
    void update(Long writeId, Write write);
    void delete(Long writeId);


}
