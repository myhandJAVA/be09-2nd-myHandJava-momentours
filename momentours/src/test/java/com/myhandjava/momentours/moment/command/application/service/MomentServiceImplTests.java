package com.myhandjava.momentours.moment.command.application.service;

import com.myhandjava.momentours.moment.command.application.dto.MomentDTO;
import com.myhandjava.momentours.moment.command.domain.aggregate.Moment;
import com.myhandjava.momentours.moment.command.domain.aggregate.MomentCategory;
import com.myhandjava.momentours.moment.command.domain.repository.MomentRepository;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByCoupleNoVO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByMomentPublicVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MomentServiceImplTests {

    @Autowired
    private MomentService momentService;
    @Autowired
    private MomentRepository momentRepository;

    @DisplayName("추억 조회 테스트")
    @Test
    void findMomentByCoupleNo() {
        // given
        int momentCoupleNo = 1;

        // when
        List<ResponseFindMomentByCoupleNoVO> result = momentService.findMomentByMomentCoupleNo(momentCoupleNo);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());

//        System.out.println("moment_category: " + result.get(0).getMomentCategory());
        result.forEach(vo -> assertEquals(momentCoupleNo, vo.getMomentCoupleNo()));
    }

    @DisplayName("공개 여부로 추억 조회 테스트")
    @Test
    void findMomentByMomentCoupleNo() {
        // given
        int momentPublic = 1;

        // when
        List<ResponseFindMomentByMomentPublicVO> result = momentService.findMomentByMomentPublic(momentPublic);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());

        System.out.println("result 공개 여부: " + result.get(0).getMomentPublic());
        result.forEach(vo -> assertTrue(vo.getMomentPublic() == 1));
    }

    @DisplayName("추억 등록 테스트")
    @Test
    void registMoment() {
        // given
        MomentDTO newMomentDTO = new MomentDTO();
        newMomentDTO.setMomentTitle("동해 바다 여행");
        newMomentDTO.setMomentCategory(MomentCategory.여행);
        newMomentDTO.setMomentContent("동해 바다를 보면 왠지 모르게 설레는 마음이 커진다.");
        newMomentDTO.setMomentPublic(1);
        newMomentDTO.setMomentLike(3);
        newMomentDTO.setMomentView(23);
        newMomentDTO.setMomentCoupleNo(1);
        newMomentDTO.setMomentIsDeleted(0);
        newMomentDTO.setMomentLongitude(37.80605);
        newMomentDTO.setMomentLatitude(128.9081);
        newMomentDTO.setMomentAddress("강원도 강릉시 안현동 산1");
        newMomentDTO.setMomentLocationName("경포대");

        // when
        momentService.registMoment(newMomentDTO);

        // then
        Moment savedMoment = momentRepository.findAll().stream()
                .filter(moment -> moment.getMomentTitle().equals("동해 바다 여행"))
                .findFirst()
                .orElse(null);

        assertNotNull(savedMoment);
        assertEquals("동해 바다 여행", savedMoment.getMomentTitle());
        assertEquals(1, savedMoment.getMomentCoupleNo());
    }

//    @DisplayName("작성자의 추억 수정 테스트")
//    @Test
//    void updateMomentByTitleAndCoupleNo() throws Exception {
//        // given
//        int momentId = 5;
//        String originalTitle = "OriginalTitle";
//        int coupleNo = 1;
//
//        // 테스트용 추억 추가
//        Moment originalMoment = new Moment();
//        originalMoment.setMomentTitle(originalTitle);
//        originalMoment.setMomentCategory(MomentCategory.맛집);
//        originalMoment.setMomentContent("과연 수정이 될까?");
//        originalMoment.setMomentCreateDate(LocalDateTime.now());
//        originalMoment.setMomentPublic(0);
//        originalMoment.setMomentLike(0);
//        originalMoment.setMomentView(0);
//        originalMoment.setMomentCoupleNo(coupleNo);
//        originalMoment.setMomentIsDeleted(0);
//        originalMoment.setMomentLongitude(37.80605);
//        originalMoment.setMomentLatitude(128.9081);
//        originalMoment.setMomentAddress("우체국주소");
//        originalMoment.setMomentLocationName("구내식당");
//
//        // 업데이트할 필드
//        MomentDTO updatedMomentDTO = new MomentDTO();
//        updatedMomentDTO.setMomentTitle("수정 제목");
//        updatedMomentDTO.setMomentCategory(MomentCategory.산책);
//        updatedMomentDTO.setMomentContent("수정됨 ㅋㅋ");
//        updatedMomentDTO.setMomentUpdateDate(LocalDateTime.now());
//        updatedMomentDTO.setMomentPublic(1);
//        updatedMomentDTO.setMomentLongitude(38.00000);
//        updatedMomentDTO.setMomentAddress("북한 어딘가");
//        updatedMomentDTO.setMomentLocationName("냉면 맛집");
//
//        // when
//        momentService.updateMomentByTitleAndCoupleNo(momentId, originalTitle, coupleNo, updatedMomentDTO);
//
//        // then
//        Moment updatedMoment = momentRepository.findById(momentId).
//    }

}