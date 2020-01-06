package org.example.springboot.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void lombokTest() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        /* lombok의 @Getter로 get메소드가, @RequiredArgsConstructor로 생성자가 자동으로 생성되는 것이 증명 */

        //then
        assertThat(dto.getName()).isEqualTo(name);  //assertj라는 테스트 검증 라이브러리의 검증 메소드, 검증하고싶은 대상을 인자로, 메소드 체이닝이 지원되어 isEqualTo와 같이 이어서 사용 가능
        assertThat(dto.getAmount()).isEqualTo(amount);  //isEqualTo는 assertThat에 있는 값과 비교하여 같을 때만 성공
        //Junit의 기본 assertThat이 아닌 assertj의 assertThat이 장점이 더 많아 사용함
    }
}
