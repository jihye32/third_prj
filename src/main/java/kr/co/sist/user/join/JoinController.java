package kr.co.sist.user.join;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/join")
@Controller
public class JoinController {

	@GetMapping("/joinFrm")
	public String joinFrm() {
		return "/join/joinFrm";
	}// joinFrm

}// class
