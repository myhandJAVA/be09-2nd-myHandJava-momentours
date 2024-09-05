package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;
import com.myhandjava.momentours.diary.command.domain.aggregate.Comment;
import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.diary.command.domain.repository.DiaryRepository;
import com.myhandjava.momentours.file.command.domain.aggregate.FileBoardSort;
import com.myhandjava.momentours.file.command.domain.aggregate.FileEntity;
import com.myhandjava.momentours.file.command.domain.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServiceImplTests {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @DisplayName("일기 등록 확인 테스트")
    @Test
    @Transactional
    void registDiary() {

        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDiaryContent("오늘은 열심히 프로젝트를 했다. 일기 등록을 끝내고 싶다.");
        diaryDTO.setDiaryUserNo(1);
        diaryDTO.setCoupleNo(1);

        Diary diary = modelMapper.map(diaryDTO, Diary.class);

        // 테스트용 파일 추가
        FileEntity initialFile = new FileEntity();
        initialFile.setFileOriginalName("initialFile.jpg");
        initialFile.setFileSaveName("initialFile.jpg");
        initialFile.setFileSize(BigDecimal.valueOf(1024));
        initialFile.setFileExtension(".jpg");
        initialFile.setFileDirectory("/uploads/");
        initialFile.setFileIsDeleted(false);
        initialFile.setFileBoardSort(FileBoardSort.diary);
        initialFile.setDiary(diary);
        fileRepository.save(initialFile);

        Assertions.assertDoesNotThrow(
                () -> diaryService.registDiary(diaryDTO)
        );

        Diary registedDiary = diaryRepository.findById(diary.getDiaryNo()).orElseThrow();
        Assertions.assertEquals("오늘은 열심히 프로젝트를 했다. 일기 등록을 끝내고 싶다.", registedDiary.getDiaryContent());
    }

    @DisplayName("일기 삭제 확인 테스트")
    @Test
    @Transactional
    void removeDiary() {
//
//        DiaryDTO diaryDTO = new DiaryDTO();
//        diaryDTO.setDiaryUserNo(2);
        Assertions.assertDoesNotThrow(() -> diaryService.removeDiary(12, 2));

        Diary deletedDiary = diaryRepository.findById(12)
                .orElseThrow(() -> new AssertionError("일기 찾을 수 없습니다."));

        List<FileEntity> deletedFiles = fileRepository.findByDiary(deletedDiary);
        for (FileEntity file : deletedFiles) {
            Assertions.assertTrue(file.isFileIsDeleted(), "isFileIsDeleted의 값이 true가 아님");
        }
    }

    @Test
    @DisplayName("일기 수정 확인 테스트")
    @Transactional
    void modifyDiary() throws IOException {
        // 테스트용 일기 추가
        Diary diary = new Diary();
        diary.setDiaryContent("수정이 이렇게 오래 걸릴줄은 몰랐다.. 다 파일 때문이야");
        diary.setDiaryCreateDate(LocalDateTime.now());
        diary.setDiaryUpdateDate(LocalDateTime.now());
        diary.setDiaryUserNo(7);
        diary.setCoupleNo(2);
        diaryRepository.save(diary);

        // 테스트용 파일 추가
        FileEntity initialFile = new FileEntity();
        initialFile.setFileOriginalName("initialFile.jpg");
        initialFile.setFileSaveName("initialFile.jpg");
        initialFile.setFileSize(BigDecimal.valueOf(1024));
        initialFile.setFileExtension(".jpg");
        initialFile.setFileDirectory("/uploads/");
        initialFile.setFileIsDeleted(false);
        initialFile.setFileBoardSort(FileBoardSort.diary);
        initialFile.setDiary(diary);
        fileRepository.save(initialFile);

        //일기 수정 호출
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDiaryContent("그래도 드디어 끝냈다!!!");
        diaryDTO.setDiaryUpdateDate(LocalDateTime.now());

        // 수정 호출
        Assertions.assertDoesNotThrow(() -> diaryService.modifyDiary(diaryDTO, 7, diary.getDiaryNo()));

        // 수정된 일기 확인
        Diary modifiedDiary = diaryRepository.findById(diary.getDiaryNo()).orElseThrow();
        Assertions.assertEquals("그래도 드디어 끝냈다!!!", modifiedDiary.getDiaryContent());

        // 파일 삭제 및 재검증
        Optional<FileEntity> deletedFile = fileRepository.findById(initialFile.getFileNo());
        Assertions.assertTrue(deletedFile.isEmpty(), "파일이 삭제되었는지 확인");
    }

    @DisplayName("댓글 등록 확인 테스트")
    @Test
    @Transactional
    void registerComment() {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentContent("댓글 등록 드디어 끝냈다!! 다음은 수정 해야징!");
        commentDTO.setCommentUserNo(3);
        commentDTO.setDiaryNo(15);

        Assertions.assertDoesNotThrow(() -> diaryService.registComment(commentDTO));
    }

    @DisplayName("댓글 삭제 확인 테스트")
    @Test
    @Transactional
    void removeComment() {
        Assertions.assertDoesNotThrow(() -> diaryService.removeComment(4, 8));
    }

    @DisplayName("댓글 수정 확인 테스트")
    @Test
    @Transactional
    void modifyComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentUserNo(1);
        commentDTO.setCommentContent("갹!! 이것만 되면 난 이제 끝이야!! 히힛");

        Assertions.assertDoesNotThrow(() -> diaryService.modifyComment(5, commentDTO));
    }

    @DisplayName("일기 임시저장 테스트")
    @Test
    @Transactional
    void registTempSave() {
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDiaryContent("오늘은 열심히 프로젝트를 했다. 일기 등록을 끝내고 싶다.");
        diaryDTO.setDiaryUserNo(1);
        diaryDTO.setCoupleNo(1);

        Diary diary = modelMapper.map(diaryDTO, Diary.class);

        // 테스트용 파일 추가
        FileEntity initialFile = new FileEntity();
        initialFile.setFileOriginalName("initialFile.jpg");
        initialFile.setFileSaveName("initialFile.jpg");
        initialFile.setFileSize(BigDecimal.valueOf(1024));
        initialFile.setFileExtension(".jpg");
        initialFile.setFileDirectory("/uploads/");
        initialFile.setFileIsDeleted(false);
        initialFile.setFileBoardSort(FileBoardSort.diary);
        initialFile.setDiary(diary);
        fileRepository.save(initialFile);

        Assertions.assertDoesNotThrow(
                () -> diaryService.registTempDiary(diaryDTO)
        );
    }

}