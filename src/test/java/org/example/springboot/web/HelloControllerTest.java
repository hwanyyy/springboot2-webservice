package org.example.springboot.web;


import org.example.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)    // 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행, 여기서는 SpringRunner라는 스프링 실행자를 사용, spring boot test와 JUnit 사이에 연결자 역할
// @WebMvcTest(HelloController.class) test 범위 지정, 관련없는 ApiController 의 빈 생성을 시도하면서 연관된 빈을 제대로 주입받지 못해 테스트가 깨짐
// Web(Spring MVC)에 집중, @Controller, @ControllerAdvice등을 사용, @Service, @Component, @Repository 등 스캔대상 아님, CustomOAuth2UserService를 스캔하지 않음
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)    // 스캔 대상에서 SecurityConfig를 제거
        }
)
public class HelloControllerTest {

    @Autowired  // Spring을 관리하는 Bean을 주입받음
    private MockMvc mvc;    // 웹API를 테스트할 때 사용, spring MVC 테스트의 시작점, 이 클래스를 통해 HTTP GET, POST등 API테스트 가능

    @WithMockUser(roles = "USER")
    @Test
    public void helloReturn() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))  // MockMvc를 통해 /hello 주소로 HTTP GET요청을 함, 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언 가능
                .andExpect(status().isOk())     // mvc.perform의 결과를 검증, HTTP Header의 Status를 검증 (200,404,500...), 여기서는 ok(200)인지만 검증
                .andExpect(content().string(hello));    // 응답 본문의 내용을 검증, Controller에서 "hello"를 return 하기 때문에 이 값이 맞는지 검증
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDtoReturn() throws Exception{
        String name = "hello";
        int amount = 1000;
        /*
        param: API테스트할 때 사용될 요청 파라미터를 설정, 단 값은 String만 허용
        jsonPath: JSON응답값을 필드별로 검증할 수 있는 메소드, $를 기준으로 필드명을 명시
         */
        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
