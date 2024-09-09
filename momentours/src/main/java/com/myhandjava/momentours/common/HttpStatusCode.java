package com.myhandjava.momentours.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HttpStatusCode {

    CONTINUE(100, HttpStatus.CONTINUE, "클라이언트가 요청을 계속해도 됨"),
    SWITCHING_PROTOCOLS(101, HttpStatus.SWITCHING_PROTOCOLS, "클라이언트가 요청한 프로토콜로 전환되고 있음"),

    OK(200, HttpStatus.OK, "요청이 성공적으로 처리됨"),
    CREATED(201, HttpStatus.CREATED, "요청이 성공적으로 처리되었으며, 새로운 리소스가 생성됨"),
    NO_CONTENT(204, HttpStatus.NO_CONTENT, "요청이 성공적으로 처리되었으나, 반환할 콘텐츠가 없음"),

    MOVED_PERMANENTLY(301, HttpStatus.MOVED_PERMANENTLY, "요청한 리소스가 영구적으로 이동됨"),
    FOUND(302, HttpStatus.FOUND, "요청한 리소스가 일시적으로 다른 위치에 있음"),
    NOT_MODIFIED(304, HttpStatus.NOT_MODIFIED, "클라이언트가 캐시된 리소스를 사용해야 함"),

    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "서버가 요청을 이해할 수 없거나 잘못된 요청"),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "인증이 필요하거나 인증 실패"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "클라이언트에게 리소스 접근 권한이 없음"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없음"),

    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 예기치 않은 오류가 발생"),
    BAD_GATEWAY(502, HttpStatus.BAD_GATEWAY, "서버가 잘못된 응답을 게이트웨이로부터 수신함"),
    SERVICE_UNAVAILABLE(503, HttpStatus.SERVICE_UNAVAILABLE, "서버가 일시적으로 요청을 처리할 수 없음");


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
