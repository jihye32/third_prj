package kr.co.sist.user.findAccount;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/findAccount")
@Controller
public class FindAccountController {
	
	@GetMapping("/findAccountFrm")
	public String findAccountFrm() {
		return "/findAccount/findAccountFrm";
	}// findAccountFrm
	
	@GetMapping("/findAccountResultFrm")
	public String findAccountResultFrm() {
		return "/findAccount/findAccountResultFrm";
	}// findAccountResultFrm

}// class
