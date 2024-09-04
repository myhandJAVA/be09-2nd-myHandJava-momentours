package com.myhandjava.momentours.moment.command.application.service;

import com.myhandjava.momentours.moment.command.application.dto.MomentDTO;
import com.myhandjava.momentours.moment.command.domain.aggregate.Moment;
import com.myhandjava.momentours.moment.command.domain.repository.MomentRepository;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByCoupleNoVO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByMomentPublicVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("MomentCommandServiceImpl")
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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        newMoment.setMomentCreateDate(LocalDateTime.now());
        newMoment.setMomentUpdateDate(LocalDateTime.now());
        momentRepository.save(modelMapper.map(newMoment, Moment.class));
        log.info("New moment: {}", newMoment);
    }

    /* 설명. 글(제목, 내용)과 지도 상 장소(위치, 주소, 이름) 수정하는 메소드 */
    @Override
    @Transactional
    public void updateMomentByTitleAndCoupleNo(int momentNo,
                                               int momentCoupleNo,
                                               MomentDTO updatedMomentDTO) throws NotFoundException{
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Optional<Moment> optionalMoment = momentRepository.findByMomentNoAndMomentCoupleNo(momentNo, momentCoupleNo);

        if (optionalMoment.isEmpty()){
            throw new NotFoundException("추억을 찾을 수 없습니다.");
        }

        Moment moment = optionalMoment.get();

        // Null 체크 후 업데이트
        if (updatedMomentDTO.getMomentTitle() != null) {
            moment.setMomentTitle(updatedMomentDTO.getMomentTitle());
        }
        if (updatedMomentDTO.getMomentContent() != null) {
            moment.setMomentContent(updatedMomentDTO.getMomentContent());
        }
        if (updatedMomentDTO.getMomentCategory() != null) {
            moment.setMomentCategory(updatedMomentDTO.getMomentCategory());
        }
        if (updatedMomentDTO.getMomentPublic() != moment.getMomentPublic()) {
            moment.setMomentPublic(updatedMomentDTO.getMomentPublic());
        }
        if (updatedMomentDTO.getMomentLongitude() != moment.getMomentLongitude()) {
            moment.setMomentLongitude(updatedMomentDTO.getMomentLongitude());
        }
        if (updatedMomentDTO.getMomentLatitude() != moment.getMomentLatitude()) {
            moment.setMomentLatitude(updatedMomentDTO.getMomentLatitude());
        }
        if (updatedMomentDTO.getMomentAddress() != null) {
            moment.setMomentAddress(updatedMomentDTO.getMomentAddress());
        }
        if (updatedMomentDTO.getMomentLocationName() != null) {
            moment.setMomentLocationName(updatedMomentDTO.getMomentLocationName());
        }

        moment.setMomentUpdateDate(LocalDateTime.now());

        momentRepository.save(moment);

    }

    /* 설명. 커플이 작성했던 추억을 삭제하는 메소드 */
    @Override
    @Transactional
    public void removeMoment(int momentNo, int momentCoupleNo) throws NotFoundException {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Optional<Moment> optionalMoment = momentRepository.findByMomentNoAndMomentCoupleNo(momentNo, momentCoupleNo);

        if (optionalMoment.isEmpty()){
            throw new NotFoundException("추억을 찾을 수 없습니다.");
        }

        Moment moment = optionalMoment.get();

        momentRepository.delete(moment);

    }

    @Override
    @Transactional
    public void softRemoveMoment(int momentNo) throws NotFoundException {

        Optional<Moment> optionalMoment = momentRepository.findByMomentNo(momentNo);

        if (optionalMoment.isEmpty()){
            throw new NotFoundException("추억을 찾을 수 없습니다.");
        }
        Moment moment = optionalMoment.get();
        moment.setMomentIsDeleted(1);

        momentRepository.save(moment);
    }

    @Override
    @Transactional
    public void incrementViewCount(int momentNo) throws NotFoundException {
        Optional<Moment> optionalMoment = momentRepository.findByMomentNo(momentNo);

        Moment moment = optionalMoment.get();
        moment.setMomentView(moment.getMomentView() + 1);
        momentRepository.save(moment);

    }

    @Override
    public MomentDTO getMomentById(int momentNo) {
        Optional<Moment> optionalMoment = momentRepository.findByMomentNo(momentNo);
        Moment moment = optionalMoment.get();

        return modelMapper.map(moment, MomentDTO.class);
    }

    @Override
    @Transactional
    public void incrementLike(int momentNo) {
        Optional<Moment> optionalMoment = momentRepository.findByMomentNo(momentNo);
        Moment moment = optionalMoment.get();
        moment.setMomentLike(moment.getMomentLike() + 1);
        momentRepository.save(moment);
    }

    @Override
    @Transactional
    public void decrementLike(int momentNo) {
        Optional<Moment> optionalMoment = momentRepository.findByMomentNo(momentNo);
        Moment moment = optionalMoment.get();
        moment.setMomentLike(moment.getMomentLike() - 1);
        momentRepository.save(moment);

    }
}

