package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.common.CommonException;
import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;
import com.myhandjava.momentours.diary.command.application.dto.TemporaryDTO;
import com.myhandjava.momentours.diary.command.domain.aggregate.Comment;
import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.diary.command.domain.aggregate.Temporary;
import com.myhandjava.momentours.diary.command.domain.repository.CommentRepository;
import com.myhandjava.momentours.diary.command.domain.repository.DiaryRepository;
import com.myhandjava.momentours.diary.command.domain.repository.TemporaryRepository;
import com.myhandjava.momentours.file.command.application.service.FileService;
import com.myhandjava.momentours.file.command.domain.aggregate.FileEntity;
import com.myhandjava.momentours.file.command.domain.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("diaryCommandServiceImpl")
@Slf4j
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final CommentRepository commentRepository;
    private final TemporaryRepository temporaryRepository;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository,
                            ModelMapper modelMapper,
                            FileService fileService,
                            FileRepository fileRepository, CommentRepository commentRepository, TemporaryRepository temporaryRepository) {
        this.diaryRepository = diaryRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.commentRepository = commentRepository;
        this.temporaryRepository = temporaryRepository;
    }

    @Override
    @Transactional
    public void registDiary(DiaryDTO newDiary) throws IOException {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        newDiary.setDiaryCreateDate(LocalDateTime.now());
        Diary diary = modelMapper.map(newDiary, Diary.class);

        diaryRepository.save(diary);

        if(newDiary.getFiles() != null && !newDiary.getFiles().isEmpty()) {
            fileService.saveFileDiary(newDiary.getFiles(), diary);
        }
    }

    @Override
    @Transactional
    public void removeDiary(int diaryNo, int userNo) {
        Diary diary = diaryRepository.findByDiaryNoAndDiaryUserNo(diaryNo, userNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_DIARY));

        diary.setDiaryIsDeleted(true);

        fileService.updateFileDiaryIsDeleted(diary);
        diaryRepository.save(diary);

    }

    @Override
    @Transactional
    public void modifyDiary(DiaryDTO diaryDTO, int userNo, int diaryNo) throws IOException {
        Diary diary = diaryRepository.findByDiaryNoAndDiaryUserNo(diaryNo, userNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_DIARY));

        diary.setDiaryContent(diaryDTO.getDiaryContent());
        diary.setDiaryUpdateDate(diaryDTO.getDiaryUpdateDate());
        
        diaryRepository.save(diary);
        fileRepository.deleteByDiary(diary);

        if(diaryDTO.getFiles() != null && !diaryDTO.getFiles().isEmpty()) {
            fileService.saveFileDiary(diaryDTO.getFiles(), diary);
        }
    }

    // 댓글 등록
    @Override
    @Transactional
    public void registComment(CommentDTO commentDTO) {
        commentDTO.setCommentCreateDate(LocalDateTime.now());
        Comment comment = modelMapper.map(commentDTO, Comment.class);

        commentRepository.save(comment);
    }

    // 댓글 삭제
    @Override
    @Transactional
    public void removeComment(int commentNo, int commentUserNo) {
        Comment comment = commentRepository.findByCommentNoAndCommentUserNo(commentNo, commentUserNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_DIARY_COMMENT));

        comment.setCommentIsDeleted(true);

        commentRepository.save(comment);
    }

    // 댓글 수정
    @Override
    @Transactional
    public void modifyComment(int commentNo, CommentDTO commentDTO) {
        Comment comment = commentRepository.findByCommentNoAndCommentUserNo(commentNo, commentDTO.getCommentUserNo())
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_DIARY_COMMENT));

        comment.setCommentContent(commentDTO.getCommentContent());
        comment.setCommentUpdateDate(LocalDateTime.now());

        commentRepository.save(comment);
    }

    // 일기 임시저장
    @Override
    @Transactional
    public void registTempDiary(DiaryDTO diaryDTO) {
        diaryDTO.setDiaryCreateDate(LocalDateTime.now());
        Diary diary = modelMapper.map(diaryDTO, Diary.class);

        diaryRepository.save(diary);

        TemporaryDTO temporaryDTO = new TemporaryDTO();
        temporaryDTO.setDiaryTempSaveDate(LocalDateTime.now());
        temporaryDTO.setDiaryNo(diary.getDiaryNo());

        Temporary temporary = modelMapper.map(temporaryDTO, Temporary.class);

        temporaryRepository.save(temporary);
    }

    // 다이어리, 댓글, 파일 삭제(hard delete)
    @Override
    public void removeAllDiary(int coupleNo) {
        List<Diary> diaryList =
                diaryRepository.findAllByCoupleNo(coupleNo)
                        .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_DIARY));

        for(Diary diary : diaryList) {
            List<Comment> commentList = commentRepository.findAllByDiaryNo(diary.getDiaryNo())
                    .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_DIARY_COMMENT));
            for(Comment comment : commentList) {
                commentRepository.delete(comment);
            }
            List<Temporary> temporaryList = temporaryRepository.findAllByDiaryNo(diary.getDiaryNo())
                    .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_DIARY_TEMPORARY));
            for(Temporary temporary : temporaryList) {
                temporaryRepository.delete(temporary);
            }
            List<FileEntity> files = fileRepository.findByDiary(diary);
            for(FileEntity file : files) {
                fileRepository.delete(file);
            }
        }
    }
}
