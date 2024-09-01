package com.myhandjava.momentours.file.command.domain.aggregate;

import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "tb_file")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FileEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileNo;

    @Column
    private String fileOriginalName;

    @Column
    private String fileSaveName;

    @Column
    private BigDecimal fileSize;

    @Column
    private String fileExtension;

    @Column
    private String fileDirectory;

    @Column
    private boolean fileIsDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_board_sort", nullable = false)
    private FileBoardSort fileBoardSort;

    @Column(nullable = true)
    private int inquiryNo;

    @Column(nullable = true)
    private int momentNo;

    @Column(nullable = true)
    private int coupleNo;

    @ManyToOne
    @JoinColumn(name = "diary_no", nullable = true)
    private Diary diary;
}
