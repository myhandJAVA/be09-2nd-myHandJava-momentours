package com.myhandjava.momentours.notification.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_no")
    private int noticeNo;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "notice_create_date")
    private LocalDateTime noticeCreateDate;

    @Column(name = "notice_update_date")
    private LocalDateTime noticeUpdateDate;

    @Column(name = "notice_user_no")
    private int noticeUserNo;

    @Column(name = "notice_is_deleted")
    private int noticeIsDeleted;

}
