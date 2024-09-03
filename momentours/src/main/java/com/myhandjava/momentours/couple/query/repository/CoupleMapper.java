package com.myhandjava.momentours.couple.query.repository;

import com.myhandjava.momentours.couple.query.dto.CoupleDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoupleMapper {
    CoupleDTO findCoupleByCoupleNo(int coupleNo);
}
