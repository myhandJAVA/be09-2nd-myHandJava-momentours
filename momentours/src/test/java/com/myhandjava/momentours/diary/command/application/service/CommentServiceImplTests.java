package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.domain.aggregate.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceImplTests {

    @Autowired
    private CommentService commentService;

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

}