package com.myhandjava.momentoursUser.kakao.controller;

import com.myhandjava.momentoursUser.command.domain.aggregate.UserEntity;
import com.myhandjava.momentoursUser.common.HttpStatusCode;
import com.myhandjava.momentoursUser.common.ResponseMessage;
import com.myhandjava.momentoursUser.kakao.client.KakaoLoginClient;
import com.myhandjava.momentoursUser.kakao.dto.KakaoTokenResponseDTO;
import com.myhandjava.momentoursUser.kakao.dto.KakaoUserInfoResponseDTO;
import com.myhandjava.momentoursUser.kakao.service.KakaoService;
import com.myhandjava.momentoursUser.query.repository.UserMapper;
import com.myhandjava.momentoursUser.query.service.UserQueryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;
    private final Environment env;
    private final UserQueryService userQueryService;
    private final UserMapper userMapper;

    @Autowired
    KakaoController(KakaoLoginClient kakaoLoginClient,
                    Environment env,
                    KakaoService kakaoService,
                    UserQueryService userQueryService,
                    UserMapper userMapper){
        this.env = env;
        this.kakaoService = kakaoService;
        this.userQueryService = userQueryService;
        this.userMapper = userMapper;
    }

    @GetMapping("kakao/login")
    public ResponseEntity<ResponseMessage> loginKakaoInfo(){
        String clientId = env.getProperty("kakao.client_id");
        String redirectUri = env.getProperty("kakao.redirect_uri");
        Map<String,Object> result = new HashMap<>();
        result.put("clientId",clientId);
        result.put("redircetUri",redirectUri);
        ResponseMessage responseMessage
                = new ResponseMessage(HttpStatusCode.OK.getCode(),"카카오 로그인 링크 정보 전달 성공",result);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/kakao/oauth2")
    public ResponseEntity<Object> GetKakaoAuth(@RequestParam String code){
        KakaoTokenResponseDTO kakaoTokenResponseDTO = kakaoService.GetKakaoToken(code);
        KakaoUserInfoResponseDTO userInfo = kakaoService.getUserInfo(kakaoTokenResponseDTO);
        String userName = userInfo.getKakao_account().getEmail();

        try{
            UserEntity userEntity = userMapper.findByUserEmail(userName);
            int userNo = userEntity.getUserNo();

            List<String> roles = new ArrayList<>();
            roles.add(userEntity.getUserRole().name().substring(5));

            Claims claims = Jwts.claims().setSubject(userName);
            claims.put("auth", roles);
            claims.put("userNo",userNo);
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis()
                            + Long.parseLong(env.getProperty("token.expiration_time"))))
                    .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                    .compact();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization","Bearer " + token);
            ResponseMessage responseMessage =
                    new ResponseMessage(HttpStatusCode.OK.getCode(),"카카오 로그인 성공",null);
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseMessage);

        } catch (Exception e){
            String nickName = userInfo.getKakao_account().getProfile().getNickname();
            Map<String,Object> result = new HashMap<>();
            result.put("userName",userName);
            result.put("nickName",nickName);
            ResponseMessage responseMessage =
                    new ResponseMessage(HttpStatusCode.FORBIDDEN.getCode(), "회원조회 실패, 회원가입 필요",result);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }
    }


}
