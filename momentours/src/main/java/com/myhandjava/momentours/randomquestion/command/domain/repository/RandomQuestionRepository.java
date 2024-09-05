package com.myhandjava.momentours.randomquestion.command.domain.repository;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RandomQuestionRepository extends JpaRepository<RandomQuestion, Long> {

    // 답변 상태가 0인 가장 최근의 질문을 내림차순으로 정렬하여 1행만 조회
    @Query("SELECT rq FROM RandomQuestion rq WHERE rq.randQuesCoupleNo = :coupleNo AND rq.randQuesReply = 0 ORDER BY rq.randQuesCreateDate DESC")
    List<RandomQuestion> findLatestUnansweredQuestion(@Param("coupleNo") int coupleNo, Pageable pageable);

}
