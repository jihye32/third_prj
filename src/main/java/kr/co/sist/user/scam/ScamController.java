package kr.co.sist.user.scam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScamController {
	

    @GetMapping("/scam") // 이 주소가 브라우저 주소창과 일치해야 함!
    public String scamPage() {
        return "/scam/scam"; 
    }

}
