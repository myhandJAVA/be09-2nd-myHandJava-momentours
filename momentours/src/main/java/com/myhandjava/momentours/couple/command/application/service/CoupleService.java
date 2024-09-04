package com.myhandjava.momentours.couple.command.application.service;

import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;

public interface CoupleService {
    void inputCoupleInfo(int userNo1, int userNo2, CoupleDTO couple);
    void updateCouple(int coupleNo, CoupleDTO couple);
    void deleteCouple(int coupleNo);
}
