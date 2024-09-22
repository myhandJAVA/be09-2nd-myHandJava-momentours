package com.myhandjava.momentoursUser.kakao.dto;

import com.myhandjava.momentoursUser.kakao.aggregate.KakaoAccount;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KakaoUserInfoResponseDTO {
    private Long id;
    private LocalDateTime connected_at;
    private LocalDateTime synched_at;
    private Map<String,String> properties;
    private KakaoAccount kakao_account;
}
