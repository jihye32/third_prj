package kr.co.sist.user.scam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScamController {
	

    @GetMapping("/scam") 
    public String scamPage() {
        return "/scam/scam"; 
    }

}
