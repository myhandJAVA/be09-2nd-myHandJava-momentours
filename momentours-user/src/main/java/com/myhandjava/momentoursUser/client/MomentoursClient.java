package com.myhandjava.momentoursUser.client;

import com.myhandjava.momentoursUser.command.applicaiton.dto.CoupleRegisterDTO;
import com.myhandjava.momentoursUser.common.ResponseMessage;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="momentours",url="localhost:8234")
public interface MomentoursClient {

    @GetMapping("/calendar/{coupleNo}")
     ResponseEntity<ResponseMessage> findAllSchedule(@PathVariable int coupleNo);

    @PostMapping("/couple")
    ResponseEntity<ResponseMessage> registerCouple(@RequestBody CoupleRegisterDTO coupleRegisterDTO);
    }
