package com.myhandjava.momentours.randomquestion.command.domain.Repository;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RandomReplyRepository extends JpaRepository<RandomReply, Integer> {
    Optional<RandomReply> findByRandomReplyNo(int randomReplyNo);
}
