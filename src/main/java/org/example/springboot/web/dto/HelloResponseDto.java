package org.example.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
Dto : 계층 간에 데이터 교환을 위한 객, Requests 데이터를 받음
 */

@Getter     // 선언된 모든 필드의 get메소드를 생성
@RequiredArgsConstructor    // 선언된 모든 final필드가 포함된 생성자를 생성, final이 없는 필드는 생성자에 포함되지 않음
public class HelloResponseDto {

    private final String name;
    private final int amount;

}