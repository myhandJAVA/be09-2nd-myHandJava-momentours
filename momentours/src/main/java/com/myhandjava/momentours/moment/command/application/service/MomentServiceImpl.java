package com.myhandjava.momentours.moment.command.application.service;

import com.myhandjava.momentours.moment.command.application.dto.MomentDTO;
import com.myhandjava.momentours.moment.command.domain.aggregate.Moment;
import com.myhandjava.momentours.moment.command.domain.repository.MomentRepository;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByCoupleNoVO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByMomentPublicVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MomentServiceImpl implements MomentService {

    private final ModelMapper modelMapper;
    private final MomentRepository momentRepository;

    @Autowired
    public MomentServiceImpl(ModelMapper modelMapper, MomentRepository momentRepository) {
        this.modelMapper = modelMapper;
        this.momentRepository = momentRepository;
    }

    @Override
    public List<ResponseFindMomentByCoupleNoVO> findMomentByMomentCoupleNo(int momentCoupleNo) {
        List<Moment> moments = momentRepository.findMomentByMomentCoupleNo(momentCoupleNo, Sort.by("momentNo").descending());
        List<MomentDTO> momentDTOs = moments.stream()
                .map(moment -> modelMapper.map(moment, MomentDTO.class))
                .collect(Collectors.toList());

        return momentDTOs.stream().
                map(dto -> modelMapper.map(dto, ResponseFindMomentByCoupleNoVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseFindMomentByMomentPublicVO> findMomentByMomentPublic(int momentPublic) {
        boolean isPublic = (momentPublic == 1);

        List<Moment> moments = momentRepository.findMomentByMomentPublic(momentPublic, Sort.by("momentNo").descending());

        List<MomentDTO> momentDTOs = moments.stream()
                .map(moment -> modelMapper.map(moment, MomentDTO.class))
                .collect(Collectors.toList());

        return momentDTOs.stream()
                .map(dto -> modelMapper.map(dto, ResponseFindMomentByMomentPublicVO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registMoment(MomentDTO newMoment) {
        momentRepository.save(modelMapper.map(newMoment, Moment.class));
    }
}
