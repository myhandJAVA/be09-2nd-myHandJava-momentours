package com.myhandjava.momentours.randomquestion.command.domain.Repository;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomQuestionRepository extends JpaRepository<RandomQuestion, Integer> {

}
