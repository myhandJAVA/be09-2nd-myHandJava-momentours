package com.myhandjava.momentours.moment.query.service;

import com.myhandjava.momentours.moment.query.dto.MomentDTO;

import java.util.List;

public interface MomentService {

    List<MomentDTO> findAllMomentByCoupleNo(MomentDTO momentDTO);

//    List<MomentDTO> findOthersMoment(int momentPublic);
//
//    List<MomentDTO> findAllMomentByLocation(String locationName);
}
