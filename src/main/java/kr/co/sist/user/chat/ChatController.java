package kr.co.sist.user.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/chat")
@Controller("UserChatController")
public class ChatController {
//	@GetMapping("/list")
//	public String testChat() {
//		return "chat/chat_list :: chatListForm";
//	}
	
	@Autowired
	private ChatService cs;
	
	@GetMapping("/init/{pnum}")
	public String newChat(@PathVariable int pnum, HttpSession session, Model model) {
		ProductDomain pd = cs.searchProduct(pnum);
		String uid = (String)session.getAttribute("uid");
		uid="user18";
		Integer room = cs.searchChatRoom(pd.getSellerId(), uid);
		
		model.addAttribute("roomNum", room);
		model.addAttribute("product", pd);
		return "chat/chat_contents :: chatContentsForm";
	}
	
	@ResponseBody
	@PostMapping("/send")
	public ChatDTO sendChat(@RequestBody ChatDTO cDTO, HttpSession session) {
		String uid = (String)session.getAttribute("uid");
		uid = "user18";
		cDTO.setWriterId(uid);
		return cs.sendMessage(cDTO);
	}
}
