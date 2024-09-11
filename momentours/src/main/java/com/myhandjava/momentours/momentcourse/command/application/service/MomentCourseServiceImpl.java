package com.myhandjava.momentours.momentcourse.command.application.service;


import com.myhandjava.momentours.common.CommonException;
import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.momentcourse.command.application.dto.FavoriteDTO;
import com.myhandjava.momentours.momentcourse.command.application.dto.MomcourselocationDTO;
import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.Favorite;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.Momcourselocation;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourse;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import com.myhandjava.momentours.momentcourse.command.domain.repository.FavoriteRepository;
import com.myhandjava.momentours.momentcourse.command.domain.repository.MomcourselocationRepository;
import com.myhandjava.momentours.momentcourse.command.domain.repository.MomentCourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service("CommandMomentCourseService")
@Slf4j
public class MomentCourseServiceImpl implements MomentCourseService {

    private final MomentCourseRepository momentCourseRepository;
    private final MomcourselocationRepository momcourselocationRepository;
    private final FavoriteRepository favoriteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MomentCourseServiceImpl(MomentCourseRepository momentCourseRepository, MomcourselocationRepository momcourselocationRepository, FavoriteRepository favoriteRepository, ModelMapper modelMapper) {
        this.momentCourseRepository = momentCourseRepository;
        this.momcourselocationRepository = momcourselocationRepository;
        this.favoriteRepository = favoriteRepository;
        this.modelMapper = modelMapper;
    }

    // 추억 코스 등록
    @Override
    @Transactional
    public void registMomentCourse(MomentCourseDTO momentCourseDTO) {
        momentCourseDTO.setMomCourseCreateDate(LocalDateTime.now());
        if(momentCourseDTO.getMomCourseSort().equals(MomentCourseSort.fewDay))
            momentCourseDTO.setMomCourseSort(MomentCourseSort.fewDay);
        else momentCourseDTO.setMomCourseSort(MomentCourseSort.oneDay);

        MomentCourse momentCourse = modelMapper.map(momentCourseDTO, MomentCourse.class);

        momentCourseRepository.save(momentCourse);

        for (Integer momentNo : momentCourseDTO.getMomentNos()) {
            MomcourselocationDTO momcourselocationDTO = new MomcourselocationDTO();
            momcourselocationDTO.setMomentNo(momentNo);
            momcourselocationDTO.setMomCourseNo(momentCourse.getMomCourseNo());

            Momcourselocation momcourselocation = modelMapper.map(momcourselocationDTO, Momcourselocation.class);

            momcourselocationRepository.save(momcourselocation);
        }

    }

    //추억 코스 삭제
    @Override
    @Transactional
    public void removeMomentCourse(int momCourseNo, int momCourseCoupleNo) {
        MomentCourse momentCourse = momentCourseRepository.findByMomCourseNoAndMomCourseCoupleNo(momCourseNo, momCourseCoupleNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_MOMENTCOURSE));

        momentCourse.setMomCourseIsDeleted(true);
        momentCourseRepository.save(momentCourse);
    }

    // 추억 코스 수정
    @Override
    @Transactional
    public void modifyMomentCourse(int momCourseNo, MomentCourseDTO momentCourseDTO) {

        momentCourseDTO.setMomCourseUpdateDate(LocalDateTime.now());
        MomentCourse momentCourse = momentCourseRepository.findByMomCourseNoAndMomCourseCoupleNo(momCourseNo, momentCourseDTO.getMomCourseCoupleNo())
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_MOMENTCOURSE));

        momentCourse.setMomCourseTitle(momentCourseDTO.getMomCourseTitle());
        momentCourse.setMomCourseMemo(momentCourseDTO.getMomCourseMemo());
        momentCourse.setMomCoursePublic(momentCourseDTO.isMomCoursePublic());
        if(momentCourseDTO.getMomCourseSort().equals(MomentCourseSort.fewDay))
            momentCourse.setMomCourseSort(MomentCourseSort.fewDay);
        else momentCourse.setMomCourseSort(MomentCourseSort.oneDay);

        momentCourseRepository.save(momentCourse);
        momcourselocationRepository.deleteByMomCourseNo(momCourseNo);

        for (Integer momentNo : momentCourseDTO.getMomentNos()) {
            MomcourselocationDTO momcourselocationDTO = new MomcourselocationDTO();
            momcourselocationDTO.setMomentNo(momentNo);
            momcourselocationDTO.setMomCourseNo(momCourseNo);

            Momcourselocation momcourselocation = modelMapper.map(momcourselocationDTO, Momcourselocation.class);

            momcourselocationRepository.save(momcourselocation);
        }
    }

    // 추억코스 좋아요
    @Override
    @Transactional
    public void incrementLike(int momCourseNo) {
        MomentCourse momentCourse = momentCourseRepository.findById(momCourseNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_MOMENTCOURSE));

        momentCourse.setMomCourseLike(momentCourse.getMomCourseLike() + 1);
        momentCourseRepository.save(momentCourse);
    }

    // 추억코스 좋아요 취소
    @Override
    @Transactional
    public void decrementLike(int momCourseNo) {
        MomentCourse momentCourse = momentCourseRepository.findById(momCourseNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_MOMENTCOURSE));

        if(momentCourse.getMomCourseLike() > 0) {
            momentCourse.setMomCourseLike(momentCourse.getMomCourseLike() - 1);
            momentCourseRepository.save(momentCourse);
        }
    }

    // 추억코스 즐겨찾기
    @Override
    @Transactional
    public boolean isFavorite(FavoriteDTO favoriteDTO) {
        MomentCourse momentCourse = momentCourseRepository.findById(favoriteDTO.getFavoMomCourseNo())
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_MOMENTCOURSE));

        // 즐겨찾기 있는지 확인
        Optional<Favorite> existingFavorite = favoriteRepository.findByFavoMomCourseNoAndFavoUserNo(favoriteDTO.getFavoMomCourseNo(), favoriteDTO.getFavoUserNo());

        if(existingFavorite.isPresent()) {
            favoriteRepository.delete(existingFavorite.get());
            return false;
        } else {
            Favorite favorite = new Favorite();
            favorite.setFavoMomCourseNo(favoriteDTO.getFavoMomCourseNo());
            favorite.setFavoUserNo(favoriteDTO.getFavoUserNo());
            favoriteRepository.save(favorite);
            return true;
        }
    }


}
