package com.myhandjava.momentours.file.query.dto;

import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.file.command.domain.aggregate.FileBoardSort;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FileDTO {

    private Integer fileNo;
    private String fileOriginalName;
    private String fileSaveName;
    private BigDecimal fileSize;
    private String fileExtension;
    private String fileDirectory;
    private boolean fileIsDeleted;
    private FileBoardSort fileBoardSort;
    private int inquiryNo;
    private int momentNo;
    private int coupleNo;
    private int diaryNo;
}
