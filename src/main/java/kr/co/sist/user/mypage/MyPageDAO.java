package kr.co.sist.user.mypage;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;
import kr.co.sist.user.ProductDomain;

@Repository
public class MyPageDAO {
	
	public MyPageDomain selectMyPageInfo(int myId) throws PersistenceException {
		MyPageDomain mpd = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		mpd = ss.selectOne("kr.co.sist.user.myPage.myPageInfo", myId);
		if(ss != null) {ss.close();}// end if
		return mpd;
	}// selectMyPageInfo
	
	public List<ProductDomain> selectMySellProduct(MyPageRangeDTO mprDTO)throws PersistenceException {
		List<ProductDomain> list = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.myPage.selectSellProduct", mprDTO);
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectMyPageInfo
	
	public List<ProductDomain> selectMyBookmarkProduct(MyPageRangeDTO mprDTO)throws PersistenceException {
		List<ProductDomain> list = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.myPage.selectBookmarkProduct", mprDTO);
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectMyBookmarkProduct
	
	public int selectMySellCnt(int storeNum) throws PersistenceException {
		int cnt = 0;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		cnt = ss.selectOne("kr.co.sist.user.myPage.selectMySellCnt", storeNum);
		if(ss != null) {ss.close();}// end if
		return cnt;
	}// selectMySellCnt
	public int selectMyBookmarkCnt(int storeNum) throws PersistenceException {
		int cnt = 0;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		cnt = ss.selectOne("kr.co.sist.user.myPage.selectMyBookmarkCnt", storeNum);
		if(ss != null) {ss.close();}// end if
		return cnt;
	}// selectMyBookmarkCnt
	
	public void deleteId(String Id) throws PersistenceException {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		ss.update("kr.co.sist.user.myPage.deleteCount", Id);
		
		if(ss != null) {ss.close();}// end if
	}// selectMyBookmarkCnt
	public void deleteProduct(int storeNum) throws PersistenceException {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		ss.update("kr.co.sist.user.myPage.deleteProduct", storeNum);
		if(ss != null) {ss.close();}// end if
	}// selectMyBookmarkCnt
	
	public void updateProfile(ProfileDTO pDTO) throws PersistenceException {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		ss.update("kr.co.sist.user.myPage.updateProfile", pDTO);
		if(ss != null) {ss.close();}// end if
	}// updateProfile
	
	public List<BuyProductDomain> selectBuyProduct(String myId) throws PersistenceException{
		List<BuyProductDomain> list =null;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.myPage.selectBuyProduct", myId);
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectBuyProduct
	
	public void updateReceive(ReviewDTO rDTO) throws PersistenceException {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		ss.update("kr.co.sist.user.myPage.updateReceive", rDTO);
		if(ss != null) {ss.close();}// end if
	}// updateReceive
	
	public void insertReview(ReviewDTO rDTO) throws PersistenceException {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		ss.insert("kr.co.sist.user.myPage.insertReview", rDTO);
		if(ss != null) {ss.close();}// end if
	}// insertReview
	
}// class
