package kr.co.sist.user.info;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/info")
@Controller
public class InfoController {
	
	@GetMapping("/passChkFrm")
	public String passChkFrm() {
		return "/info/passChkFrm";
	}// passChkFrm
	
	@GetMapping("/infoFrm")
	public String infoFrm() {
		return "/info/infoFrm";
	}// infoFrm
	
}// class
