package com.myhandjava.momentours.notification.command.application.service;

import com.myhandjava.momentours.notification.command.application.dto.NotificationDTO;
import com.myhandjava.momentours.notification.command.domain.aggregate.Notification;
import com.myhandjava.momentours.notification.command.domain.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final ModelMapper modelMapper;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(ModelMapper modelMapper, NotificationRepository notificationRepository) {
        this.modelMapper = modelMapper;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void registNotification(NotificationDTO newNotification) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        newNotification.setNoticeCreateDate(LocalDateTime.now());
        newNotification.setNoticeUpdateDate(LocalDateTime.now());
        notificationRepository.save(modelMapper.map(newNotification, Notification.class));
        log.info("New notification registered: " + newNotification);

    }
}
