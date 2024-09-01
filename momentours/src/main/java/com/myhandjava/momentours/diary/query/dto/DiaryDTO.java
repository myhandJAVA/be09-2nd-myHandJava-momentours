package com.myhandjava.momentours.diary.query.dto;

import com.myhandjava.momentours.file.query.dto.FileDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiaryDTO {
    private int diaryNo;
    private String diaryContent;
    private String diaryCreateDate;
    private String diaryUpdateDate;
    private int diaryUserNo;
    private int coupleNo;
    private int diaryIsDeleted;

    private List<FileDTO> files;

}



