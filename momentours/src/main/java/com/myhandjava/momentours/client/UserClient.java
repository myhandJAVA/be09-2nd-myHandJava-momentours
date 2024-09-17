package com.myhandjava.momentours.client;

import com.myhandjava.momentours.common.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="momentours-user",url="localhost:8123")
public interface UserClient {
    @GetMapping("/users/{userNo}/partner")
    ResponseEntity<ResponseMessage> findPartnerByUserNo(@PathVariable int userNo);

    @GetMapping("/users/{userNo}/coupleNo")
    ResponseEntity<ResponseMessage> findCoupleNoByUserNo(@PathVariable int userNo);
}
