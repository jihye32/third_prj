package kr.co.sist.user.login;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/login")
@Controller("userLoginController")
public class LoginController {

	@Autowired
	private LoginService ls;

	@GetMapping("/loginFrm")
	public String loginFrm() {
		return "login/loginFrm";
	}// loginFrm

	@PostMapping("/loginProcess")
	public String loginProcess(LoginDTO lDTO, HttpSession session, RedirectAttributes ra) {
		try {
			MemberDomain md = ls.loginMember(lDTO);

			session.setAttribute("uid", md.getUser_id());
			session.setAttribute("snum", md.getStore_num());
			
			return "redirect:/";
		} catch (PersistenceException pe) {
			ra.addFlashAttribute("loginFlag", "fail");
			ra.addFlashAttribute("errorMsg", pe.getMessage());

			return "redirect:/user/login/loginFrm";
		}// end catch
	}// loginProcess

}// class
