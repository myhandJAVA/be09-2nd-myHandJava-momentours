package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.domain.aggregate.Comment;
import com.myhandjava.momentours.diary.command.domain.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceImplTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("댓글 등록 확인 테스트")
    @Test
    @Transactional
    void registerComment() {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentContent("댓글 등록 드디어 끝냈다!! 다음은 수정 해야징!");
        commentDTO.setCommentUserNo(3);
        commentDTO.setDiaryNo(15);

        Assertions.assertDoesNotThrow(() -> commentService.registComment(commentDTO));
    }

    @DisplayName("댓글 삭제 확인 테스트")
    @Test
    @Transactional
    void removeComment() {
        Assertions.assertDoesNotThrow(() -> commentService.removeComment(4, 8));
        Comment comment = commentRepository.findById(4).get();
        Assertions.assertTrue(comment.isCommentIsDeleted(), "isCommentIsDeleted가 true가 아님");
    }

    @DisplayName("댓글 수정 확인 테스트")
    @Test
    @Transactional
    void modifyComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentUserNo(1);
        commentDTO.setCommentContent("갹!! 이것만 되면 난 이제 끝이야!! 히힛");

        Assertions.assertDoesNotThrow(() -> commentService.modifyComment(5, commentDTO));
    }

}