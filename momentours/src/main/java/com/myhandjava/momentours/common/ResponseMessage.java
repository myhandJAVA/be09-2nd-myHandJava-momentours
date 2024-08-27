package com.myhandjava.momentours.common;

import java.util.Map;

// 백엔드에서 프론트엔드에서 쓸 법한 데이터를 뭉탱이로 만들어 놓은 객체, 뭐가 필요할지 모르는 일단 다 긁어 모음
public class ResponseMessage {
    private int httpStatus;      // 응답 상태 코드
    private String message;      // 응답 메세지
    private Map<String, Object> result;  // 응답 데이터

    public ResponseMessage() {
    }

    public ResponseMessage(int httpStatus, String message, Map<String, Object> result) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.result = result;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "httpStatus=" + httpStatus +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
