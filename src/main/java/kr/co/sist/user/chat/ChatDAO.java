package kr.co.sist.user.chat;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository("UserChatDAO")
public class ChatDAO {
	//선택된 상품 번호로 상품의 정보 조회
	public ProductDomain selectProduct(int pnum) {
		ProductDomain pd = null;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		pd=ss.selectOne("kr.co.sist.user.chat.selectProductInfo", pnum);
		
		if( ss != null) { ss.close(); }//end if
		
		return pd;
	}//selectProduct
	
	public Integer selectChatRoom(ChatListDTO clDTO) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		Integer roomNum=ss.selectOne("kr.co.sist.user.chat.selectChatRoom", clDTO);
		
		if( ss != null) { ss.close(); }//end if
		
		return roomNum;
	}//selectChatRoom
	
	public int insertChatRoom(ChatListDTO clDTO) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		int cnt=ss.insert("kr.co.sist.user.chat.insertChatRoom", clDTO);
		
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//insertChatRoom

	public int insertChatProduct(ChatDTO cDTO) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		int cnt=ss.insert("kr.co.sist.user.chat.insertRoomProduct", cDTO);
		
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//insertChatProduct
	
	public String selectChatProduct(ChatDTO cDTO) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		String cnt=ss.selectOne("kr.co.sist.user.chat.selectChatProduct", cDTO);
		
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//insertChatProduct
	
	public int updateLastChat(ChatListDTO clDTO) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		int cnt=ss.update("kr.co.sist.user.chat.updateLastChat", clDTO);
		
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//insertChatProduct
	public int insertChat(ChatDTO cDTO) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		int cnt=ss.update("kr.co.sist.user.chat.insertChat", cDTO);
		
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//insertChatProduct
}
