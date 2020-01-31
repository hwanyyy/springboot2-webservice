package org.example.springboot.web;


import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;


/*
ProfileController나 Environment 모두 자바 클래스(인터페이스)이기 때문에 쉽게 테스트 할 수 있다.
Environment는 인터페이스라 가짜 구현체인 MockEnvironment(스프링에서 제공)을 사용해서 테스트하면 된다.
Environment를 @Autowired로 DI받았다면 이런 테스트 코드를 작성하지 못하고, 항상 스프링 테스트를 해야한다. 이로써 생성자 DI가 얼마나 유용한지 알 수 있다.
그리고 이 /profile이 인증없이도 호출될 수 있게 SecurityConfig 클래스에 제외 코드를 추가합니다.
.antMatchers("profile").permitAll()
 */

public class ProfileControllerUnitTest {

    @Test   // real_profile이 조회된다.
    public void real_profile_check(){
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test   // real_profile이 없으면 첫번째가 조회된다.
    public void real_profile_first_check(){
        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test   // active_profile이 없으면 default가 조회된다.
    public void active_profile_default(){
        //give
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);

    }

}
