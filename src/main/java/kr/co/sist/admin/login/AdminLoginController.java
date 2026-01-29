package kr.co.sist.admin.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {
	
	@GetMapping("/manage") //
	public String loginPage() {
	    return "manage/login/login"; // 앞의 /를 꼭 제거해 보세요.
	}
}
