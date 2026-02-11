package kr.co.sist.user.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/chat")
@Controller("UserChatController")
public class ChatController {
	
	@Autowired
	private ChatService cs;
	
	@GetMapping("/list")
	public String chatList(HttpSession session, Model model) {
		String uid = (String)session.getAttribute("uid");
		uid="user10";
		
		List<ChatListDomain> clDomain = cs.searchChatList(uid);  
		model.addAttribute("list", clDomain);
		
		return "chat/chat_list :: chatListForm";
	}
	
	@GetMapping("/{otherId}")
	public String chat(@PathVariable String otherId, @RequestParam(required = false) Integer pnum, HttpSession session, Model model) {
		String uid = (String)session.getAttribute("uid");
		uid="user10";
		Integer room = cs.searchChatRoom(otherId, uid);
		if(room != null) {
			//이전에 대화한 기록 가져오기
			List<ChatDomain> chatList = cs.searchChat(room, uid);
			model.addAttribute("chatList", chatList);
		}
		ProductDomain pd = null;
		if(pnum != null) {
			pd=cs.searchProduct(pnum);
		}
		
		model.addAttribute("product", pd);
		model.addAttribute("otherId", otherId);
		model.addAttribute("roomNum", room);
		return "chat/chat_contents :: chatContentsForm";
	}
	
	@ResponseBody
	@PostMapping("/send")
	public ChatDTO sendChat(@RequestBody ChatDTO cDTO, HttpSession session) {
		String uid = (String)session.getAttribute("uid");
		uid = "user10";
		cDTO.setWriterId(uid);
		return cs.sendMessage(cDTO);
	}
}
