package org.example.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest     // 별다른 설정 없으면 H2 db자동으로 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    /* Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정, 보통은 배포 전 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용
       여러 테스트가 동시에 수행되면 테스트용 db인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패 할 수 있음
     */
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void postSaveRead(){
        //given
        String title = "test title";
        String content = "test content";

        postsRepository.save(Posts.builder()    // 테이블 posts에 insert/update 쿼리를 실행, id값이 있다면 update 없으면 insert쿼리가 실행 됨
                .title(title)
                .content(content)
                .author("goooogler94@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();  // 테이블 posts에 있는 모든 데이터 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntitySave() {
        //given
        LocalDateTime now = LocalDateTime.of(2020,1,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);


    }

}
