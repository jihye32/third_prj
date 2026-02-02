package kr.co.sist.user.productlist;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;
import kr.co.sist.user.ProductDomain;
 
@Repository
public class ProductListDAO {
	
	public List<CategoryDomain> selectCategory() throws PersistenceException{
		List<CategoryDomain> list = null;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		list = ss.selectList("kr.co.sist.user.productlist.selectCategory");
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectCategory
	
	public List<ProductDomain> selectProductList() throws PersistenceException{
		List<ProductDomain> list = null;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		list = ss.selectList("kr.co.sist.user.productlist.selectProdcutList");
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectCategory
	
	public int selectProductTotalCnt(ProductRangeDTO prDTO) {
		int cnt = 0;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		cnt = ss.selectOne("kr.co.sist.user.productlist.selectProdcutCnt");
		if(ss != null) {ss.close();}// end if
		return cnt;
	}// selectProductTotalCnt
	
}// class
