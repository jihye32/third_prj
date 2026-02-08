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
	
	public List<ProductDomain> selectAllProduct(SellerPageRangeDTO sprDTO) throws PersistenceException {
		List<ProductDomain> list = null; 
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.sellerPage.selectAllProduct", sprDTO);
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectAllProduct
	
}
