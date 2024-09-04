package com.myhandjava.momentours.notification.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.notification.command.application.dto.NotificationDTO;
import com.myhandjava.momentours.notification.command.application.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "notificationCommandController")
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/regist")
    public ResponseEntity<ResponseMessage> registNotification(NotificationDTO newNotification) {
        notificationService.registNotification(newNotification);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newNotification", newNotification);

        ResponseMessage responseMessage = new ResponseMessage(201,
                "공지 등록이 완료되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }
}
