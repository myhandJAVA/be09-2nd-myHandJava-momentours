package com.myhandjava.momentours.randomquestion.query.repository;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface RandomReplyMapper {
    RandomReply findRandomReplyByQuestionNoAndUserNo(Map<String, Object> map);
}
