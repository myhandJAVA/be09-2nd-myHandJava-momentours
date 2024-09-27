package com.myhandjava.momentours.couple.command.application.service;

import com.myhandjava.momentours.common.CommonException;
import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import com.myhandjava.momentours.couple.command.domain.aggregate.Couple;
import com.myhandjava.momentours.couple.command.domain.repository.CoupleRepository;
import com.myhandjava.momentours.diary.command.application.service.DiaryService;
import com.myhandjava.momentours.moment.command.application.service.MomentService;
import com.myhandjava.momentours.randomquestion.command.application.service.RandomQuestionAndReplyCommandService;
import com.myhandjava.momentours.schedule.command.application.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "coupleCommandService")
@Slf4j
public class CoupleServiceImpl implements CoupleService {

    private final CoupleRepository coupleRepository;
    private final RandomQuestionAndReplyCommandService randomQuesCommandService;
    private final DiaryService diaryService;
    private final MomentService momentService;
    private final ScheduleService scheduleService;

    public CoupleServiceImpl(CoupleRepository coupleRepository, MomentService momentService,
                             RandomQuestionAndReplyCommandService randomQuesCommandService,
                             DiaryService diaryService, ScheduleService scheduleService) {
        this.coupleRepository = coupleRepository;
        this.randomQuesCommandService = randomQuesCommandService;
        this.diaryService = diaryService;
        this.momentService = momentService;
        this.scheduleService = scheduleService;
    }

    @Override
    @Transactional
    public void updateCouple(int coupleNo ,CoupleDTO newInfo) {
        Couple couple = coupleRepository.findByCoupleNo(coupleNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_COUPLE));
        if(newInfo.getCoupleName() != couple.getCoupleName())
            couple.setCoupleName(newInfo.getCoupleName());
        if(newInfo.getCouplePhoto() != couple.getCouplePhoto())
            couple.setCouplePhoto(newInfo.getCouplePhoto());
        if(newInfo.getCoupleStartDate() != couple.getCoupleStartDate())
            couple.setCoupleStartDate(newInfo.getCoupleStartDate());
        coupleRepository.save(couple);
    }

    @Override
    @Transactional
    public int registCouple(int userNo1, int userNo2) {
        Couple newCouple = new Couple();
        newCouple.setCoupleUserNo1(userNo1);
        newCouple.setCoupleUserNo2(userNo2);
        newCouple.setCoupleIsDeleted(0);
        coupleRepository.save(newCouple);
        return newCouple.getCoupleNo();
    }

    @Transactional
    @Override
    public void inputCoupleInfo(int coupleNo, CoupleDTO coupleInfo) {
        Couple couple = coupleRepository.findByCoupleNo(coupleNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_COUPLE));
        couple.setCoupleName(coupleInfo.getCoupleName());
        couple.setCouplePhoto(coupleInfo.getCouplePhoto());
        couple.setCoupleStartDate(coupleInfo.getCoupleStartDate());
        coupleRepository.save(couple);
    }

    @Transactional
    @Override
    public void deleteCouple(int coupleNo, int userNo) {
        Couple couple = coupleRepository.findByCoupleNo(coupleNo)
                .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_COUPLE));
        couple.setCoupleIsDeleted(1);
        // 랜덤질문 모두 삭제
        randomQuesCommandService.removeAllRandomQuestionAndReply(coupleNo);
        // 일기 모두 삭제
        diaryService.removeAllDiary(coupleNo);
//        momentService.removeAllMoment(coupleNo);
//        scheduleService.removeAllSchedule(coupleNo);
        coupleRepository.save(couple);
    }
}
