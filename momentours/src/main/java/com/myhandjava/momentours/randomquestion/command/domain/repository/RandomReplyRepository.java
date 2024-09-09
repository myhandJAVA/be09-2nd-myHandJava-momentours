package com.myhandjava.momentours.randomquestion.command.domain.repository;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RandomReplyRepository extends JpaRepository<RandomReply, Integer> {
    Optional<RandomReply> findByRandomReplyNoAndRandomReplyUserNo(int randomReplyNo, int userNo);
    List<RandomReply> findRandomReplyByRandomQuestionNo(int questionNo);
}
