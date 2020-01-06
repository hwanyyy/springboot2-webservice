package org.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // JpaAuditing annotation 모두 활성화
@SpringBootApplication  // spring boot의 자동설정, 스프링 Bean읽기와 생성 모두 자동으로 설정, 이 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치
public class Application {  // Application 클래스는 메인 클래스
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS서버 실행 -> 항상 서버에 톰캣을 설치할 필요 X
        /* spring boot에서 내장 WAS를 권장하는 이유 -> 언제 어디서나 같은 환경에서의 spring boot를 배포 가능
           외장 WAS를 쓴다고 하면 모든 서버는 WAS의 종류와 버전, 설정을 일치시켜야함..
         */
    }
}