package kr.co.sist.user.chat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

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
			if("Y".equals(cld.getDeleteFlag())) {
				content = "탈퇴한 회원입니다.";
			}else if("Y".equals(cld.getSusFlag())) {
				content="정지된 회원입니다.";
			}else if("IMAGE".equals(cld.getContentType())) {
				content="사진을 보냈습니다.";
			}else if(content.length() > 20) {
				content = content.substring(0, 20)+"...";
			}
			cld.setContent(content);
			
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
		
		String deleteFlag = cs.searchDelete(otherId);
		String susFlag = cs.searchSuspension(otherId);
		
		model.addAttribute("suspensionFlag", susFlag);
		model.addAttribute("deleteFlag", deleteFlag);
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
		cDTO.setType("TEXT");
		ChatDTO cc= cs.sendMessage(cDTO);

		messagingTemplate.convertAndSend("/topic/room/" + cc.getRoomNum(), cc);


	    return cc;
	}
	
	@ResponseBody
	@PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ChatDTO uploadChatImage(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam(value="roomNum", required=false) Integer roomNum,
	        @RequestParam(value="productNum", required=false) Integer productNum,
	        @RequestParam("otherId") String otherId,
	        HttpSession session) throws Exception {

	    String uid = (String) session.getAttribute("uid");

	    ChatDTO cDTO = new ChatDTO();
	    cDTO.setWriterId(uid);
	    cDTO.setOtherId(otherId);
	    cDTO.setRoomNum(roomNum);
	    cDTO.setProductNum(productNum);

	    if (cDTO.getProductNum() == null && cDTO.getRoomNum() != null) {
	        cDTO.setProductNum(cs.searchProductNum(cDTO.getRoomNum()));
	    }

	    //파일 저장하고 URL 만들기
	    String imageUrl = cs.saveChatImageToStatic(file); // "/images/chat/xxx.jpg" 형태로 리턴

	    //IMAGE 메시지로 저장
	    cDTO.setType("IMAGE");
	    cDTO.setContent(imageUrl);

	    ChatDTO saved = cs.sendMessage(cDTO);

	    // ✅ 3) 실시간 브로드캐스트
	    messagingTemplate.convertAndSend("/topic/room/" + saved.getRoomNum(), saved);

	    return saved;
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
