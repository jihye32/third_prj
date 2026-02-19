package kr.co.sist.user.sellerpage;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;
import kr.co.sist.user.ProductDomain;

@Repository
public class SellerPageDAO {

	public SellerPageDomain selectSeller(int storeNum) throws PersistenceException {
		SellerPageDomain spd = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		spd = ss.selectOne("kr.co.sist.user.sellerPage.selectSellerInfo", storeNum);
		if(ss != null) {ss.close();}// end if
		return spd;
	}// selectSeller
	
	public List<ProductDomain> selectProductList(SellerPageRangeDTO sprDTO) throws PersistenceException {
		List<ProductDomain> list = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.sellerPage.selectProductList", sprDTO);
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectProductList
	
	public int selectProductListCnt ( SellerPageRangeDTO sprDTO)throws PersistenceException{
		int cnt = 0;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		cnt = ss.selectOne("kr.co.sist.user.sellerPage.selectProdcutListCnt", sprDTO);
		if(ss != null) {ss.close();}// end if
		return cnt;
	}// selectProductListCnt
	
	public String selectSellerId(int storeNum) {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		String id = ss.selectOne("kr.co.sist.user.sellerPage.selectSellerId", storeNum);
		if(ss != null) {ss.close();}// end if
		return id;
	}// selectSellerId
	
	public int selectCountReview(int storeNum) {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		int reviewCnt = ss.selectOne("kr.co.sist.user.sellerPage.selectCountReview", storeNum);
		if(ss != null) {ss.close();}// end if
		return reviewCnt;
	}// selectCountReview
	
}// class