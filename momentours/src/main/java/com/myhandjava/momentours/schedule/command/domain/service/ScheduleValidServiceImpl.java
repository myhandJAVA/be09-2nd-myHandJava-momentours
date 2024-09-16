package com.myhandjava.momentours.schedule.command.domain.service;

import com.myhandjava.momentours.client.UserClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ScheduleValidServiceImpl implements ScheduleValidService{

    private final UserClient userClient;
    private final Environment env;
    private final String jwtKey;

    @Autowired
    private ScheduleValidServiceImpl(UserClient userClient,
                                     Environment env){
        this.userClient = userClient;
        this.env = env;
        jwtKey = env.getProperty("token.secret");
    }

    @Override
    public boolean isValidUserNoAndCoupleNo(Integer bodyUserNo, Integer bodyCoupleNo, String token) {
        boolean isValidNo = true;

        token = token.substring(7);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Integer tokenUserNo = (Integer)claims.get("userNo");

        if (!bodyUserNo.equals(tokenUserNo)) {
            isValidNo = false;
            return isValidNo;
        }

        if (bodyCoupleNo != null) {
            Integer tokenCoupleNo = (Integer)userClient.findCoupleNoByUserNo(bodyUserNo)
                    .getBody()
                    .getResult()
                    .get("coupleNo");
            isValidNo = bodyCoupleNo.equals(tokenCoupleNo);
        }

        return isValidNo;
    }
}
