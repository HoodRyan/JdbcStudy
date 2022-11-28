package com.example.demo.common;

import com.example.demo.member.entity.Member;

public class MemberFixtures {
    /* 일반 사용자 */
    public static final String 일반_사용자_이름1 = "태용";
    public static final String 일반_사용자_닉네임1 = "라이언";
    public static final String 일반_사용자_이름2 = "원진";
    public static final String 일반_사용자_닉네임2 = "프로도";

    /* 회원가입 데이터*/
    public static Member 멤버_회원가입1 = new Member(null, 일반_사용자_이름1, 일반_사용자_닉네임1);
    public static Member 멤버_회원가입2 = new Member(null, 일반_사용자_이름2, 일반_사용자_닉네임2);
}
