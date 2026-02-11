package kr.co.sist.user.chat;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository("UserChatDAO")
public class ChatDAO {
	//채팅 리스트
	public List<ChatListDomain> selectChatList(String uid){
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		List<ChatListDomain> list=ss.selectList("kr.co.sist.user.chat.selectChatList", uid);
		
		if( ss != null) { ss.close(); }//end if
		
		return list;
	}
	
	public ChatListInfoDomain selectStoreName(String otherId) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		ChatListInfoDomain clid=ss.selectOne("kr.co.sist.user.chat.selectStoreName", otherId);
		
		if( ss != null) { ss.close(); }//end if
		
		return clid;
	}
	
	public String selectProductProfile(int pnum) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		String thumbnail=ss.selectOne("kr.co.sist.user.chat.selectProductThumbnail", pnum);
		
		if( ss != null) { ss.close(); }//end if
		
		return thumbnail;
	}
	
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
	
	public List<ChatDomain> selectChat(int roomNum) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		List<ChatDomain> list=ss.selectList("kr.co.sist.user.chat.selectChat", roomNum);
		
		if( ss != null) { ss.close(); }//end if
		
		return list;
	}//insertChatProduct
	
	public int updateReadChat(ChatDTO cDTO) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		int cnt=ss.update("kr.co.sist.user.chat.updateReadChat", cDTO);
		
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//insertChatProduct
	
	public Integer selectProductNum(int roomNum) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		Integer pnum=ss.selectOne("kr.co.sist.user.chat.selectProductNum", roomNum);
		
		if( ss != null) { ss.close(); }//end if
		
		return pnum;
	}
}
