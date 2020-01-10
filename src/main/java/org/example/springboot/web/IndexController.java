package org.example.springboot.web;

import lombok.RequiredArgsConstructor;
import org.example.springboot.config.auth.LoginUser;
import org.example.springboot.config.auth.dto.SessionUser;
import org.example.springboot.service.PostsService;
import org.example.springboot.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 페이지와 관련된 controller
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
//    private final HttpSession httpSession;

    /*
    Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장, 여기서는 findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달
    @LoginUser: index메소드외에 다른 컨트롤러와 메소드에서 세션값이 필요하면 그때마다 직접 세션에서 값을 가져와야하므로 코드 반복 -> 메소드 인자로 세션값을 바로 받을 수 있도록 변경(annotaion 활용)
     */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

//        SessionUser user = (SessionUser) httpSession.getAttribute("user");    //CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장했으니, 로그인 성공시 값을 가져옴

        if(user != null){
            model.addAttribute("userName", user.getName());     // session에 저장된 값이 있을 때만 model에 userName으로 등록
        }
        /*
        mustache starter 덕분에 Controller에서 문자열을 반환할 때 앞의 경로(src/main/resources/templates)와 뒤의 파일 확장자(/index.mustache)는 자동으로 지정
        src/main/resources/templates/index.mustache로 전환되어 View Resolver(URL 요청의 결과를 전달할 타입과 값을 지정하는 관리자)가 처리
         */
        return "index";     //index.mustache
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}
