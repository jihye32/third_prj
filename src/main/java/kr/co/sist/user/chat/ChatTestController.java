package kr.co.sist.user.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChatTestController {
	@GetMapping("/login")
	public String devLogin(@RequestParam String uid, HttpSession session) {
	    session.setAttribute("uid", uid);
	    return "redirect:/product/1";
	  }
}
