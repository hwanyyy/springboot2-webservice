package org.example.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springboot.domain.posts.Posts;

/*
Dto는 Entity class와 거의 유사한 형태임에도 따로 생성함, 절대로 Entity class를 Request/Response class로 사용해서는 안됨
Entity class는 db와 맞닿음 핵심 class이기 때문.. 수많은 class와 비즈니스 로직들이 Entity class를 기준으로 동작함
Request와 Response용 Dto는 View를 위한 클래스라 정말 자주 변경 필요
Controller에서 결과값으로 여러 테이블을 조인해서 줘야 할 경우가 빈번하므로 Entity class만으로 표현하기 어려운 경우가 많음

-> View layer와 DB layer의 역할 분리를 철저

 */
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder    // 빌더에 포함시키면 toEntity()처럼 어느 필드에 어떤 값을 채워야 할 지 명확하게 인지
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
    // 생성자 만들때 포함된 title, content, author만 빌더에 포함되어 사용 가능
    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
