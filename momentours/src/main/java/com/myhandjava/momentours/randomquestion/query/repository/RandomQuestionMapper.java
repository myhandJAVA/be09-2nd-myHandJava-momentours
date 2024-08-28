package com.myhandjava.momentours.randomquestion.query.repository;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RandomQuestionMapper {
    RandomQuestion findCurrentRandomQuestionByMemberNo(int userNo);
}
