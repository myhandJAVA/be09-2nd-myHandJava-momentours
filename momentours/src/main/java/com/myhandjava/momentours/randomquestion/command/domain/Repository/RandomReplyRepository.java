package com.myhandjava.momentours.randomquestion.command.domain.Repository;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomReplyRepository extends JpaRepository<RandomReply, Integer> {
    RandomReply findByRandomQuestionNoAndRandomReplyUserNo(int randomreplyno, int randomreplyuserno);
}
