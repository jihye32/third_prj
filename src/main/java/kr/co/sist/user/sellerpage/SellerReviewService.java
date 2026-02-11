package kr.co.sist.user.sellerpage;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.dao.MyBatisHandler;

@Service
public class SellerReviewService {
	
	@Autowired
	sellerReviewDAO srDAO;
	
	public List<SellerReviewDTO> selectReviews(String storeId) throws PersistenceException {
	    
	    return srDAO.selectReviews(storeId);
	}
	public double selectReviewStar(String storeId) throws PersistenceException {
		return srDAO.selectReviewStar(storeId);
	}
	public int selectCountReview(String storeId) throws PersistenceException {
		return srDAO.selectCountReview(storeId);
	}


}
