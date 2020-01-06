package org.example.springboot.web.dto;

import lombok.Getter;
import org.example.springboot.domain.posts.Posts;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    /*
        Entity의 필드 중 일부만 사용하므로 생성자로 Entity를 받아 필드에 값을 넣음, 굳이 모든 필드를 가진 생성자가 필요하진 않으므로
        Q: Entity전체 필드 다 쓰지 않나? 근데 Entity가져오는게 아니라면 다른 방법은?? 오히려 반대 아닌가?
    */
    public PostsResponseDto(Posts enttiy){
        this.id = enttiy.getId();
        this.title = enttiy.getTitle();
        this.content = enttiy.getContent();
        this.author = enttiy.getAuthor();
    }

}
