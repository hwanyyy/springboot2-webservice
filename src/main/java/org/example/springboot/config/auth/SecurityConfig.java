package org.example.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.springboot.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  //spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()     //h2-console화면을 사용하기 위한 해당 옵션들을 disable
                .and()
                    .authorizeRequests()    // URL별 권한 관리를 설정하는 옵션의 시작점, 선언되어야 antMathers옵션 사용가능
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()  // 권한 관리 대상을 지정, URL, HTTP 메소드별로 관리가 가능, 등 지정된 URL들은 permitAll()로 전체 열람 권한 줌
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // POST 메소드이면서 "/api/v1/**"주소를 가진 API는 USER권한을 가진 사람만 가능
                    .anyRequest().authenticated()   // 설정된 값들 이외 나머지 URL, authenticated()을 추가하여 나머지 URL들은 모두 인증된(로그인 한) 사용자들에게만 허용
                .and()
                    .logout()   //로그아웃기능에 대한 여러 설정의 진입점, 성공시 "/"주소로 이동
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()  // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint() // //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                            .userService(customOAuth2UserService);  //소셜로그인 성공 시 후속 조치를 진행할 UserService interface의 구현체를 등록, 리소스서버(소셜서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음
    }
}
