package kr.co.sist.user.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/chat")
@Controller("UserChatController")
public class ChatController {
	@GetMapping("/list")
	public String testChat() {
		return "chat/chat_list :: chatListForm";
	}
	@GetMapping("/contents")
	public String testChatContents() {
		return "chat/chat_contents :: chatContentsForm";
	}
}
