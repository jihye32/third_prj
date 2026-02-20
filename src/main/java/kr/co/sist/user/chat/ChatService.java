package kr.co.sist.user.chat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("UserChatService")
public class ChatService {

	@Autowired
	private ChatDAO cDAO;
	
	//채팅 리스트 확인
	public List<ChatListDomain> searchChatList(String uid){
		List<ChatListDomain> list = cDAO.selectChatList(uid);
		
		for(ChatListDomain cld : list) {
			ChatListInfoDomain clid = cDAO.selectStoreName(cld.getOtherId());
			
			cld.setProfile(clid.getProfile());
			cld.setStoreName(clid.getStoreName());
			cld.setThumbnail(cDAO.selectProductProfile(cld.getPnum()));
			cld.setDeleteFlag(cDAO.selectDelete(cld.getOtherId()));
			cld.setSusFlag(cDAO.selectSuspension(cld.getOtherId()));
		}
		return list;
	}
	public String searchDelete(String id) {
		return cDAO.selectDelete(id);
	}
	public String searchSuspension(String id) {
		return cDAO.selectSuspension(id);
	}
	
	//거래하는 상품 정보
	public ProductDomain searchProduct(int pnum) {
		ProductDomain pd = cDAO.selectProduct(pnum);
		return pd;
	}//searchProduct
	
	//채팅방 확인
	public Integer searchChatRoom(String sellerId, String userId) {
		ChatListDTO clDTO = new ChatListDTO();
		String me = userId;
		String other = sellerId;

		String low = me.compareTo(other) < 0 ? me : other;
		String high = me.compareTo(other) < 0 ? other : me;

		clDTO.setLowId(low);
		clDTO.setHighId(high);
		Integer chatRoomNum = cDAO.selectChatRoom(clDTO);
		return chatRoomNum;
	}//searchChatRoom

	
	//채팅 보내기
	public ChatDTO sendMessage(ChatDTO cDTO) {
		Integer roomNum = cDTO.getRoomNum();
		Integer productNum = cDTO.getProductNum();

		ChatListDTO clDTO = new ChatListDTO();

		if(productNum != null) {
			clDTO.setPnum(productNum);
		}
		clDTO.setContent(cDTO.getContent());
		clDTO.setContentType(cDTO.getType());
		
		String me = cDTO.getWriterId();
		String other = cDTO.getOtherId();

		String low = me.compareTo(other) < 0 ? me : other;
		String high = me.compareTo(other) < 0 ? other : me;
		
		clDTO.setLowId(low);
		clDTO.setHighId(high);
		
		//기록이 없는 경우 : 방만들기, 상품만들기
		if(roomNum == null) {
			if(cDAO.insertChatRoom(clDTO)==1) {
				roomNum = cDAO.selectChatRoom(clDTO);
			}
			
			cDTO.setRoomNum(roomNum);
		}
		
		//상품 있는지 확인하기
		if(cDAO.selectChatProduct(cDTO)==null && productNum != null) {
			cDAO.insertChatProduct(cDTO);
		}
		
		cDAO.insertChat(cDTO);
		cDAO.updateLastChat(clDTO);
		
		return cDTO;
	}
	
	//이미지 보내기
	public String saveChatImageToStatic(MultipartFile file) throws IOException {
	    // 확장자 체크(최소)
	    String original = file.getOriginalFilename();
	    String ext = (original != null && original.contains("."))
	            ? original.substring(original.lastIndexOf(".")).toLowerCase()
	            : ".jpg";

	    if (!ext.equals(".png") && !ext.equals(".jpg") && !ext.equals(".jpeg")) {
	        throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
	    }

	    // 파일명은 UUID로
	    String filename = UUID.randomUUID().toString().replace("-", "") + ext;

	    // ✅ 로컬 개발에서만 가능한 경로 (프로젝트 소스 폴더)
	    String basePath = System.getProperty("user.dir");
	    Path saveDir = Paths.get(basePath, "src", "main", "resources", "static", "images", "chat");
	    Files.createDirectories(saveDir);

	    Path savePath = saveDir.resolve(filename);
	    file.transferTo(savePath.toFile());

	    // 웹에서 접근할 URL 리턴
	    return "/upload/chat/" + filename;
	}

	
	public List<ChatDomain> searchChat(int roomNum, String uid){
		// 읽음 처리 먼저
		ChatDTO cDTO = new ChatDTO();
		cDTO.setWriterId(uid);
		cDTO.setRoomNum(roomNum);
	    cDAO.updateReadChat(cDTO);

	    // 그 다음 조회
	    return cDAO.selectChat(roomNum);
	}
	
	public Integer searchProductNum(int room) {
		return cDAO.selectProductNum(room);
	}
	
	public void modifyRead(ChatDTO chatDTO) {
		cDAO.updateReadChat(chatDTO);
	}
}
