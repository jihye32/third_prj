package kr.co.sist.user;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;
  
@Repository
public class MainDAO {
	
	public List<ProductDomain> selectMostViewProdcut() throws PersistenceException{
		List<ProductDomain> list = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.main.selectMostViewProdcut");
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectMostViewProdcut
	
	public List<ProductDomain> selectMostLikeProdcut() throws PersistenceException{
		List<ProductDomain> list = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.main.selectMostLikeProdcut");
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectMostViewProdcut
	
	public void insertHistory(String ip) throws PersistenceException{
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		ss.insert("kr.co.sist.user.main.insertHistory", ip);
		if(ss != null) {ss.close();}// end if
	}// insertHistory
	
	
}// class
