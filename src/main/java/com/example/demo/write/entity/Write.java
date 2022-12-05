package com.example.demo.write.entity;

import com.example.demo.member.entity.Member;
import lombok.Data;

@Data
public class Write {

    private Long writeId;
    private String title;
    private String content;
    private Long member_id;

    public Write() {

    }
    public Write(Long writeId, String title, String content, Long memberId){
        this.writeId = writeId;
        this.title = title;
        this.content = content;
        this.member_id = memberId;
    }


    public void changeWrite(Write updateWrite) {
        this.title = updateWrite.getTitle();
        this.content = updateWrite.getContent();
    }

//    public Long getWriteId() {
//        return writeId;
//    }
//    public String getTitle(){
//        return title;
//    }
//    public String getContent() {
//        return content;
//    }
//    public Long getMember_id() {
//        return member_id;
//    }
}
