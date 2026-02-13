package kr.co.sist.user.sell;

import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class SellDAO {
	
	public int selectProductNum() throws PersistenceException {
		int productNum = 0; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		productNum = ss.selectOne("kr.co.sist.user.sell.selectProductNum");
		if(ss != null) {ss.close();}// end if
		return productNum;
	}// selectProductNum
	
	public int insertProduct(SellDTO sDTO, SqlSession ss) throws PersistenceException {
		int cnt = 0;
		cnt = ss.insert("kr.co.sist.user.sell.insertProduct", sDTO);
		return cnt;
	}// insertProduct
	
	public int insertImages(Map<String, Object> map, SqlSession ss) throws PersistenceException {
		int cnt = 0;
		cnt = ss.insert("kr.co.sist.user.sell.insertImages", map);
		return cnt;
	}// insertImages
	
	public int insertTradeType(Map<String, Object> map, SqlSession ss) throws PersistenceException {
		int cnt = 0;
		cnt = ss.insert("kr.co.sist.user.sell.insertTradeType", map);
		return cnt;
	}// insertTradeType
	
	public int insertTradeArea(Map<String, Object> map, SqlSession ss) throws PersistenceException {
		int cnt = 0;
		cnt = ss.insert("kr.co.sist.user.sell.insertTradeArea", map);
		return cnt;
	}// insertTradeArea
		
	
}// class
