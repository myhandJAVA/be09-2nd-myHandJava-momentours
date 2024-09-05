package com.myhandjava.momentours.notification.command.application.service;

import com.myhandjava.momentours.notification.command.application.dto.NotificationDTO;

public interface NotificationService {

    void registNotification(NotificationDTO newNotification);
}
