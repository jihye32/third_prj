package kr.co.sist.user.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/support")
@Controller("userSupportController")
public class SupportController {

	@GetMapping("/supportFrm")
	public String supportFrm() {
		return "/support/supportFrm";
	}// supportFrm
	
	@GetMapping("/supportDetailFrm")
	public String supportDetailFrm() {
		return "/support/supportDetailFrm";
	}// supportDetailFrm
	
	@GetMapping("/askFrm")
	public String askFrm() {
		return "/support/askFrm";
	}// askFrm
	
	@GetMapping("/askDetailFrm")
	public String askDetailFrm() {
		return "/support/askDetailFrm";
	}// askDetailFrm
	
	@GetMapping("/askWriteFrm")
	public String askWriteFrm() {
		return "/support/askWriteFrm";
	}// askWriteFrm
	
}// class
