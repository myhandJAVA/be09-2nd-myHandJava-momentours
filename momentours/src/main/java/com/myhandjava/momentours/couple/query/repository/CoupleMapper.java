package com.myhandjava.momentours.couple.query.repository;

import com.myhandjava.momentours.couple.query.dto.CoupleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CoupleMapper {
    CoupleDTO findCoupleByCoupleNo(int coupleNo);
    Map<String, Object> getCoupleInfo(int coupleNo);
    List<String> getTopDateCategories(int coupleNo);
}
