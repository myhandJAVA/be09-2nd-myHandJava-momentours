package com.myhandjava.momentours.diary.command.application.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class DiaryDTO {
    private String diaryContent;
    private LocalDateTime diaryCreateDate;
    private LocalDateTime diaryUpdateDate;
    private int diaryUserNo;
    private int coupleNo;
    private int diaryIsDeleted;

    private List<MultipartFile> files;
}
