package org.example.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 각 사용자의 권한을 관리할 Enum Class
@Getter
@RequiredArgsConstructor
public enum Role {
    // spring security에서는 권한 코드에 항상 ROLE_이 앞에 있어야 함, 그래서 코드별 키 값을 ROLE_GUEST, ROLE_USER 등으로 지정
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
