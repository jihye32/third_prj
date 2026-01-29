package kr.co.sist.user.scam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScamController {
	

    @GetMapping("/scam") // 이 주소가 브라우저 주소창과 일치해야 함!
    public String scamPage() {
        // templates/manage/login/login.html 파일을 찾아가게 함
        return "/scam/scam"; 
    }

}
