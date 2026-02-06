package kr.co.sist.user.join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/join")
@Controller
public class JoinController {

	@Autowired
	private JoinService js;
	
	@GetMapping("/joinFrm")
	public String joinFrm() {
		return "/join/joinFrm";
	}// joinFrm
	
//	@ResponseBody
//	@GetMapping("/idDup")
//	public String idDup(String id) {
//		
//	}// idDup
//	
//	@ResponseBody
//	@GetMapping("/storeNameDup")
//	public String storeNameDup(String storeName) {
//		
//	}// storeNameDup
//	
//	@ResponseBody
//	@GetMapping("/sendVerifyCode")
//	public String sendVerifyCode(String email, HttpSession session) {
//		
//	}// sendVerifyCode
//	
//	@ResponseBody
//	@PostMapping("/verifyCodeChk")
//	public String verifyCodeChk(String verifyCode, HttpSession session) {
//		
//	}// verifyCodeChk
//	
//	@PostMapping("/joinProcess")
//	public String joinProcess(JoinDTO jDTO, RedirectAttributes ra) {
//	    boolean result = js.addMember(jDTO);
//	    
//	    if (result) {
//	    	ra.addFlashAttribute("flag", "success");
//	        return "redirect:/join/joinSuccess"; 
//	    } else {
//	    	ra.addFlashAttribute("flag", "fail");
//	        return "redirect:/join/joinFrm";
//	    }// end else   
//	}// joinProcess
	
	@GetMapping("/joinSuccess")
	public String joinSuccess() {
		return "/join/joinSuccess";
	}// joinSuccess

}// class
