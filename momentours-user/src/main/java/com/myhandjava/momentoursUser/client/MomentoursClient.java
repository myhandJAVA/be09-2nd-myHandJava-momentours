package com.myhandjava.momentoursUser.client;

import com.myhandjava.momentoursUser.common.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="momentours",url="localhost:8234")
public interface MomentoursClient {

    @GetMapping("/calendar/{coupleNo}")
     ResponseEntity<ResponseMessage> findAllSchedule(@PathVariable int coupleNo);

    }
