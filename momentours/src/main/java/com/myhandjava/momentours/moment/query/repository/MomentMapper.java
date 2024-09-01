package com.myhandjava.momentours.moment.query.repository;

import com.myhandjava.momentours.moment.query.dto.MomentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MomentMapper {

    List<MomentDTO> findAllMomentByCoupleNo(MomentDTO momentDTO);

//    public List<MomentDTO> findOthersMoment(int momentPublic);
//
//    public List<MomentDTO> findAllMomentByLocation(String locationName);
}
