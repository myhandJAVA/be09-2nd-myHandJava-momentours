package com.myhandjava.momentours.moment.query.service;

import com.myhandjava.momentours.moment.query.dto.MomentDTO;
import com.myhandjava.momentours.moment.query.repository.MomentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("queryMomentServiceImpl")
@Slf4j
public class MomentServiceImpl implements MomentService {

    private final MomentMapper momentMapper;

    @Autowired
    MomentServiceImpl(MomentMapper momentMapper) {
        this.momentMapper = momentMapper;
    }

    /* 설명. 커플번호를 통해 해당 커플의 추억을 모두 불러옴 */
    @Override
    public List<MomentDTO> findAllMomentByCoupleNo(MomentDTO momentDTO) {
        List<MomentDTO> momentDTOList = momentMapper.findAllMomentByCoupleNo(momentDTO);

        return momentDTOList;
    }

//    @Override
//    public List<MomentDTO> findOthersMoment(int momentPublic) {
//        List<MomentDTO> momentDTOList = momentMapper.findOthersMoment(momentPublic);
//
//        return momentDTOList;
//    }
//
//    @Override
//    public List<MomentDTO> findAllMomentByLocation(String locationName) {
//        List<MomentDTO> momentDTOList = momentMapper.findAllMomentByLocation(locationName);
//
//        return momentDTOList;
//    }
}
