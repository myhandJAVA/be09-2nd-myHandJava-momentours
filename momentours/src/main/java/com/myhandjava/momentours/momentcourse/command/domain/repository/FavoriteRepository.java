package com.myhandjava.momentours.momentcourse.command.domain.repository;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Optional<Favorite> findByFavoMomCourseNoAndFavoUserNo(int favoMomCourseNo, int favoUserNo);
}
