package kr.co.sist.user.join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
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

	@ResponseBody
	@GetMapping("/idDup")
	public boolean idDup(@RequestParam String id) {
		return js.searchId(id);
	}// idDup

	@ResponseBody
	@GetMapping("/storeNameDup")
	public boolean storeNameDup(@RequestParam String storeName) {
		return js.searchStoreName(storeName);
	}// storeNameDup

	@ResponseBody
	@GetMapping("/sendVerifyCode")
	public boolean sendVerifyCode(@RequestParam String emailLocal, @RequestParam String emailDomain, HttpSession session) {
	    String email = emailLocal + emailDomain;
	    String verifyCode = String.valueOf((int)(Math.random() * 900000) + 100000);

	    session.setAttribute("verifyCode", verifyCode);
	    session.setAttribute("verifyEmail", email);

	    boolean result = js.sendAuthEmail(email, verifyCode);

	    return result;
	}// sendVerifyCode

	@ResponseBody
	@PostMapping("/verifyCodeChk")
	public boolean verifyCodeChk(@RequestParam String inputCode, HttpSession session) {
		String sessionCode = (String) session.getAttribute("verifyCode");

		if (sessionCode == null) {
	        return false; // 세션 만료
	    }// end if

	    return sessionCode.equals(inputCode);
	}// verifyCodeChk

	@PostMapping("/joinProcess")
	public String joinProcess(JoinDTO jDTO, HttpServletRequest request, HttpSession session, RedirectAttributes ra) {
		jDTO.setIp(request.getRemoteAddr());
		boolean result = js.joinMember(jDTO);

		if (result) {
			session.setAttribute("uid", jDTO.getId());
	        session.setAttribute("snum", jDTO.getStoreNum());
	        
			return "redirect:/join/joinSuccess";
		} else {
			ra.addFlashAttribute("joinFlag", "fail");
			
			return "redirect:/join/joinFrm";
		} // end else
	}// joinProcess

	@GetMapping("/joinSuccess")
	public String joinSuccess() {
		return "/join/joinSuccess";
	}// joinSuccess

}// class
