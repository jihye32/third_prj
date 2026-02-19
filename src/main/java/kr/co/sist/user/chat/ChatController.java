package kr.co.sist.user.chat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@GetMapping("/list")
	public String chatList(HttpSession session, Model model) {
		String uid = (String)session.getAttribute("uid");
		
		List<ChatListDomain> clDomain = cs.searchChatList(uid);  
		for(ChatListDomain cld : clDomain) {
			String content = cld.getContent();
			if(content.length() > 20) {
				cld.setContent(content.substring(0, 20)+"...");
			}
		}
		model.addAttribute("list", clDomain);
		
		return "chat/chat_list :: chatListForm";
	}
	
	@GetMapping("/{otherId}")
	public String chat(@PathVariable String otherId, @RequestParam(required = false) Integer pnum, HttpSession session, Model model) {
		String uid = (String)session.getAttribute("uid");
		
		ProductDomain pd = null;
		
		Integer room = cs.searchChatRoom(otherId, uid);
		if(room != null) {
			//이전에 대화한 기록 가져오기
			List<ChatDomain> chatList = cs.searchChat(room, uid);
			model.addAttribute("chatList", chatList);
			
			messagingTemplate.convertAndSend("/topic/room/" + room, Map.of("type","READ","roomNum",room,"readerId",uid));
			
			if(pnum == null) {
				pnum = cs.searchProductNum(room);
			}
		}
		if(pnum != null) {
			pd=cs.searchProduct(pnum);
			model.addAttribute("pnum", pnum);
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
		cDTO.setWriterId(uid);
		
		if(cDTO.getProductNum()==null) {
			cDTO.setProductNum(cs.searchProductNum(cDTO.getRoomNum()));
		}
		ChatDTO cc= cs.sendMessage(cDTO);

		messagingTemplate.convertAndSend("/topic/room/" + cc.getRoomNum(), cc);


	    return cc;
	}
	
	
	@ResponseBody
	@PostMapping("/read")
	public void modifyMessage(@RequestBody ChatDTO cDTO, HttpSession session) {
		String uid = (String)session.getAttribute("uid");
		cDTO.setWriterId(uid);
		
		cs.modifyRead(cDTO);
		
		messagingTemplate.convertAndSend("/topic/room/" + cDTO.getRoomNum(), Map.of("type","READ","roomNum",cDTO.getRoomNum(),"readerId",uid));
	}
	
}
