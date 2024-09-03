package com.myhandjava.momentours.moment.command.application.service;

import com.myhandjava.momentours.moment.command.application.dto.MomentDTO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByCoupleNoVO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByMomentPublicVO;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;

public interface MomentService {

    List<ResponseFindMomentByCoupleNoVO> findMomentByMomentCoupleNo(int momentCoupleNo);

    List<ResponseFindMomentByMomentPublicVO> findMomentByMomentPublic(int momentPublic);

    void registMoment(MomentDTO newMoment);

    void updateMomentByTitleAndCoupleNo (int momentNo, int momentCoupleNo, MomentDTO updatedMomentDTO) throws NotFoundException;

    void removeMoment(int momentNo, int momentCoupleNo) throws NotFoundException;

    void increamentViewCount(int momentNo) throws NotFoundException;

    MomentDTO getMomentById(int momentNo);
}
