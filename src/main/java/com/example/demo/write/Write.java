package com.example.demo.write;

public class Write {

    private Long writeId;
    private String title;
    private String content;
    private Long memberId;


    public Write(Long writeId, String title, String content){
        this.writeId = writeId;
        this.title = title;
        this.content = content;

    }

    public Long getWriteId() {
        return writeId;
    }
    public String getTitle(){
        return title;
    }
    public String getContent() {
        return content;
    }
    public void changeWrite(Write updateWrite) {
        this.title = updateWrite.getTitle();
        this.content = updateWrite.getContent();
    }

}
