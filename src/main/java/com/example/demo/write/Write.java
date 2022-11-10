package com.example.demo.write;

public class Write {

    private Long writeId;
    private String title;
    private String content;

    public Write(Long writeId, String title, String content){
        this.writeId = writeId;
        this.title = title;
        this.content = content;
    }

    public Long getWriteId() {
        return writeId;
    }

    public void setWriteId(Long memberId) {
        this.writeId = memberId;
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
