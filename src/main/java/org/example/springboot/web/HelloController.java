package org.example.springboot.web;

import org.example.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Controller는 Api의 요청을 받음
@RestController     // JSON을 반환하는 controller로 만들어 줌 (예전에 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용하게 함)
public class HelloController {

    @GetMapping("/hello")   // Get의 요청을 받을 수 있는 API
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){

        // @RequestParam: 외부에서 API로 넘긴 파라미터를 가져오는 annotation, 외부에서 name (@RequestParam("name"))이란 이름으로 넘긴 파라미터를 메소드 파라미터 name(String name)에 저장
        // http://localhost:8080/hello/dto?name=hwany&amount=1234

        return new HelloResponseDto(name, amount);
    }
}
