package kr.co.sist.admin.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

	@GetMapping("/manage/member/member_main") // 브라우저 주소창에 들어갈 주소
	public String memberMainPage(Model model) {
	    
	    model.addAttribute("menu", "member"); 
	    model.addAttribute("subMenu", "list");
	    
	    return "manage/member/member_main"; 
	}
}