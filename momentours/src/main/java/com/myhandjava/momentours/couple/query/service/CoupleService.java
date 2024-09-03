package com.myhandjava.momentours.couple.query.service;

import com.myhandjava.momentours.couple.query.dto.CoupleDTO;

public interface CoupleService {
    CoupleDTO findCoupleByCoupleNo(int coupleNo);
}
