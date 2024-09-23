package com.myhandjava.momentoursUser.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import feign.Body;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KakaoTokenRequestDTO {
    @JsonProperty("grant_type")
    private String grantType;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("redirect_uri")
    private String redirectUri;
    @JsonProperty("code")
    private String code;
}
