package com.example.demo.web.controller.write;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WriteForm {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private Long memberId;
}
