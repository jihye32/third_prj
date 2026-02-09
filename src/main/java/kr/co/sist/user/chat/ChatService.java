package kr.co.sist.user.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserChatService")
public class ChatService {

	@Autowired(required = false)
	private ChatDAO cDAO;
	
	public ProductDomain searchProduct(int pnum) {
		ProductDomain pd = cDAO.selectProduct(pnum);
		return pd;
	}//searchProduct
	
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

	
	@Transactional 
	public ChatDTO sendMessage(ChatDTO cDTO) {
		Integer roomNum = cDTO.getRoomNum();
		Integer productNum = cDTO.getProductNum();
		ProductDomain pd = searchProduct(productNum);
		
		ChatListDTO clDTO = new ChatListDTO();
		clDTO.setPnum(productNum);
		clDTO.setContent(cDTO.getContent());
		
		String me = cDTO.getWriterId();
		String other = pd.getSellerId();

		String low = me.compareTo(other) < 0 ? me : other;
		String high = me.compareTo(other) < 0 ? other : me;
		
		clDTO.setLowId(low);
		clDTO.setHighId(high);
		
		//기록이 없는 경우 : 방만들기, 상품만들기
		if(roomNum == null) {
			if(cDAO.insertChatRoom(clDTO)==1) {
				roomNum = cDAO.selectChatRoom(clDTO);
			}
			
			System.out.println(roomNum);
			
			cDAO.insertChatProduct(cDTO);
		}else {
			//기록이 있는 경우 : 상품이 있는지 확인하고 없으면 상품 만들기
			if(cDAO.selectChatProduct(cDTO)==null) {
				cDAO.insertChatProduct(cDTO);
			}
		}
		cDAO.insertChat(cDTO);
		cDAO.updateLastChat(clDTO);
		
		return cDTO;
	}
}
