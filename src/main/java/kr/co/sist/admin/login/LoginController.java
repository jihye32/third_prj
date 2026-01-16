package kr.co.sist.admin.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/manager_login") // 이 주소가 브라우저 주소창과 일치해야 함!
    public String loginPage() {
        // templates/manage/login/login.html 파일을 찾아가게 함
        return "manage/login/login"; 
    }
}