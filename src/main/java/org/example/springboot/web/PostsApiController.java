package org.example.springboot.web;

import lombok.RequiredArgsConstructor;
import org.example.springboot.service.PostsService;
import org.example.springboot.web.dto.PostsResponseDto;
import org.example.springboot.web.dto.PostsSaveRequestDto;
import org.example.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

// 아키텍처: UI -> Controller Layer -> Service Layer -> Repository Layer -> Domain Layer -> DB
@RequiredArgsConstructor
@RestController     //Controller는 Service를 호출해서 받은 결과를 UI에 전달
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")    //Service에서 만든 메소드를 컨트롤러가 사용하도록 코드 추가
    public Long delete(@PathVariable Long id){
        postsService.delete(id);

        return id;
    }

}
