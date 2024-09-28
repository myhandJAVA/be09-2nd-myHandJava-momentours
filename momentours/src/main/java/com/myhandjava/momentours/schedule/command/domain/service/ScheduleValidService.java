package com.myhandjava.momentours.schedule.command.domain.service;

public interface ScheduleValidService {
    boolean isValidUserNoAndCoupleNo(Integer bodyUserNo, Integer bodyCoupleNo, String token);

    int getTokenUserNo(String token);
}
