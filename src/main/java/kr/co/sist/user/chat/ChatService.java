package kr.co.sist.user.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserChatService")
public class ChatService {

	@Autowired
	private ChatDAO cDAO;
	
	//채팅 리스트 확인
	public List<ChatListDomain> searchChatList(String uid){
		List<ChatListDomain> list = cDAO.selectChatList(uid);
		if(list == null) return list;
		for(ChatListDomain cld : list) {
			ChatListInfoDomain clid = cDAO.selectStoreName(cld.getOtherId());
			
			cld.setProfile(clid.getProfile());
			cld.setStoreName(clid.getStoreName());
			cld.setThumbnail(cDAO.selectProductProfile(cld.getPnum()));
		}
		return list;
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
