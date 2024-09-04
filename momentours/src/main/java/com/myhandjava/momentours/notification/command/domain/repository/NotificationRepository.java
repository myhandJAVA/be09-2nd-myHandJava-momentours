package com.myhandjava.momentours.notification.command.domain.repository;

import com.myhandjava.momentours.notification.command.domain.aggregate.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
