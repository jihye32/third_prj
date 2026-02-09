package kr.co.sist.user.sellerpage;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class SellerPageDAO {

	public SellerPageDomain selectSeller(int storeNum) throws PersistenceException {
		SellerPageDomain spd = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		spd = ss.selectOne("kr.co.sist.user.sellerPage.selectSellerInfo", storeNum);
		if(ss != null) {ss.close();}// end if
		return spd;
	}// selectSeller
	
}
