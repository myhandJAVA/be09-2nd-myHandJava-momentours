package com.myhandjava.momentours.couple.command.application.service;

import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import com.myhandjava.momentours.couple.command.domain.aggregate.Couple;
import com.myhandjava.momentours.couple.command.domain.repository.CoupleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "coupleCommandService")
@Slf4j
public class CoupleServiceImpl implements CoupleService {

    private final ModelMapper modelMapper;
    private final CoupleRepository coupleRepository;

    public CoupleServiceImpl(ModelMapper modelMapper, CoupleRepository coupleRepository) {
        this.modelMapper = modelMapper;
        this.coupleRepository = coupleRepository;
    }

    @Override
    @Transactional
    public void updateCouple(int coupleNo ,CoupleDTO newInfo) {
        Couple couple = coupleRepository.findByCoupleNo(coupleNo);

        if(newInfo.getCoupleName() != couple.getCoupleName())
            couple.setCoupleName(newInfo.getCoupleName());

        if(newInfo.getCouplePhoto() != couple.getCouplePhoto())
            couple.setCouplePhoto(newInfo.getCouplePhoto());

        if(newInfo.getCoupleStartDate() != couple.getCoupleStartDate())
            couple.setCoupleStartDate(newInfo.getCoupleStartDate());

        log.info("수정된 커플 정보: {}", couple);
        coupleRepository.save(couple);
    }

    @Override
    public void inputCoupleInfo(int userNo1, int userNo2, CoupleDTO couple) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Couple newCouple = modelMapper.map(couple, Couple.class);
        newCouple.setCoupleUserNo1(userNo1);
        newCouple.setCoupleUserNo2(userNo2);

        log.info("입력된 커플 정보: {}", newCouple);
        coupleRepository.save(newCouple);
    }
}
