package kr.co.sist.admin.login;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminLoginController {
	
	@Autowired
	AdminLoginService als;
	
	@GetMapping("/manage") //
	public String loginPage() {
	    return "manage/login/login"; 
	}
	
	
	
	
	@PostMapping("/adminLoginProcess")
	public String loginProcess(AdminLoginDTO dto, HttpSession session) throws SQLException {

		System.out.println("로그인 시도id: "+dto.getAdminId());
	   boolean ok=als.login(dto);
	    if(!ok) {
	    	  return "redirect:/manage?error=1";
	    }

	    session.setAttribute("loginAdmin", dto.getAdminId());
	    return "redirect:/manage/dashboard";
	}

}
