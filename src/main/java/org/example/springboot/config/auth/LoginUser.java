package org.example.springboot.config.auth;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  // 이 annotation이 생성될 수 있는 위치를 지정, PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있음, 이외에도 클래스 선언문에 쓸 수 있는 TYPE등이 있음
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
    //@interface: 이 파일을 annotation class로 지정 (LoginUser라는 annotation이 생성)
}
