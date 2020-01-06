package org.example.springboot.service;

import lombok.RequiredArgsConstructor;
import org.example.springboot.domain.posts.Posts;
import org.example.springboot.domain.posts.PostsRepository;
import org.example.springboot.web.dto.PostsListResponseDto;
import org.example.springboot.web.dto.PostsResponseDto;
import org.example.springboot.web.dto.PostsSaveRequestDto;
import org.example.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
Service : transaction, domain기능 간의 순서만 보장, 비지니스 로직 처리(Domain) X
 */
@RequiredArgsConstructor    // 생성자로 bean주입
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /*
    영속성 컨텍스트 : Entity를 영구 저장하는 환경, JPA의 핵심 내용은 Entity가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈림

    update기능에 db에 query를 날리는 부분이 없음 -> JPA의 영속성 컨텍스트 때문 -> Transaction안에서 db에서 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태
    -> 이 상태에서 해당 데이터 값을 변경하면 Transaction이 끝나는 시점에 해당 테이블에 변경분을 반영

    => 즉, Entity객체 값만 변경하면 별도로 Update query를 날릴 필요가 없음 (dirty checking)
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public PostsResponseDto findById (Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // transaction범위는 유지하되, 조회기능만 남겨두어 조회 속도가 개선
    public List<PostsListResponseDto> findAllDesc(){
        //postsRepository 결과로 넘어 온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // == .map(posts -> new postsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        //존재하는 Posts인지 확인을 위해 Entity조회 후 삭제
        postsRepository.delete(posts);  //JpaRepository에서 이미 delete method를 지원, Entity를 파라미터로 삭제할 수도 있고, deleteById()를 사용해 id로 삭제할 수 있음
    }
}

