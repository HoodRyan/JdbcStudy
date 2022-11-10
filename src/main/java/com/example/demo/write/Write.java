package com.example.demo.write;

public class Write {

    private Long memberId;
    private String title;
    private String content;

    public Write(Long memberId, String title, String content){
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
