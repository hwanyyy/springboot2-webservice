package org.example.springboot.config.auth.dto;

import lombok.Getter;
import org.example.springboot.domain.user.User;

import java.io.Serializable;

/*
세션에 사용자 정보를 저장하기 위한 Dto Class
왜 User class를 쓰지 않고, 만들어 쓰는지?
->
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    // 인증된 사용자 정보만 필요 나머진 제외
    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
