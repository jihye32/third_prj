package kr.co.sist.user.sellerpage;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class sellerReviewDAO {
	
	public List<SellerReviewDTO> selectReviews(String storeId) throws PersistenceException {
	    List<SellerReviewDTO> list = null; 
	    SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
	    
	    // 데이터가 여러 개일 가능성이 높으므로 selectList 사용
	    list = ss.selectList("kr.co.sist.user.sellerpage.selectReviews", storeId);
	    
	    if(ss != null) { ss.close(); }
	    return list;
	}
	public double selectReviewStar(String storeId) throws PersistenceException {
		double star =0; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		// 데이터가 여러 개일 가능성이 높으므로 selectList 사용
		star = ss.selectOne("kr.co.sist.user.sellerpage.selectReviewStar", storeId);
		
		if(ss != null) { ss.close(); }
		return star;
	}
	public int selectCountReview(String storeId) throws PersistenceException {
		int count = 0; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		// 데이터가 여러 개일 가능성이 높으므로 selectList 사용
		count = ss.selectOne("kr.co.sist.user.sellerpage.selectCountReview", storeId);
		
		if(ss != null) { ss.close(); }
		return count;
	}

}
