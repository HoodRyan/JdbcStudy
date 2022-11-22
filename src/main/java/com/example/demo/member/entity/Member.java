package com.example.demo.member.entity;

import com.example.demo.write.Write;

public class Member {

    private Long id;
    private String name;
    private String nickname;


    public Member(){

    }
    public Member(Long id, String name, String nickname){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
    }

    public Long getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getNickname() {
        return nickname;
    }

}
