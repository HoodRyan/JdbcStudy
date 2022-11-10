package com.example.demo.member;

public class Member {

    private Long id;
    private String name;
    private String nickname;


    public Member(Long id, String name, String nickname){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
