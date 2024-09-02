package com.myhandjava.momentours.diary.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {
    private int commentNo;
    private String commentContent;
    private String commentCreateDate;
    private String commentUpdateDate;
    private int commentUserNo;
    private boolean commentIsDeleted;
}
