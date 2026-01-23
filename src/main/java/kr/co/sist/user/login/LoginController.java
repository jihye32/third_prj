package kr.co.sist.user.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user/login")
@Controller("userLoginController")
public class LoginController {

	@GetMapping("/loginFrm")
	public String loginFrm() {
		return "/login/loginFrm";
	}// loginFrm

}// class
