package kr.co.test.design; // 본인의 패키지명에 맞게 수정

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String testPage() {
        return "test"; // templates/test.html을 호출합니다.
    }
    @GetMapping("/")
    public String mainPage() {
    	return "user/index"; // templates/test.html을 호출합니다.
    }
}