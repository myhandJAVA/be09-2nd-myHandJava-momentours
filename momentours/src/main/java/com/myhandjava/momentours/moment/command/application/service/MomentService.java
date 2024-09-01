package com.myhandjava.momentours.moment.command.application.service;

import com.myhandjava.momentours.moment.command.application.dto.MomentDTO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByCoupleNoVO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByMomentPublicVO;

import java.util.List;

public interface MomentService {

    List<ResponseFindMomentByCoupleNoVO> findMomentByMomentCoupleNo(int momentCoupleNo);

    List<ResponseFindMomentByMomentPublicVO> findMomentByMomentPublic(int momentPublic);

    void registMoment(MomentDTO newMoment);
}
