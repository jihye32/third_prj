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
	    return "manage/login/login"; // 앞의 /를 꼭 제거해 보세요.
	}
	
	
	
	
	@PostMapping("/adminLoginProcess")
	public String loginProcess(AdminLoginDTO dto, HttpSession session) throws SQLException {

	   boolean ok=als.login(dto);
	    if(!ok) {
	    	  return "redirect:/manage/login?error=1";
	    }

	    session.setAttribute("loginAdmin", dto.getAdminId());
	    return "redirect:/manage/dashboard/dashboard";
	}

}
