package com.myhandjava.momentours.notification.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotificationDTO {
    private int noticeNo;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime noticeCreateDate;
    private LocalDateTime noticeUpdateDate;
    private int noticeUserNo;
    private int noticeIsDeleted;

}
