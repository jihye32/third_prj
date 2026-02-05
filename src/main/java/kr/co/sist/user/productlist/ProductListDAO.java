package kr.co.sist.user.productlist;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;
import kr.co.sist.user.ProductDomain;
 
@Repository
public class ProductListDAO {
	
	/**
	 * 카테고리 전체 조회
	 * @return
	 * @throws PersistenceException
	 */
	public List<CategoryDomain> selectCategory() throws PersistenceException{
		List<CategoryDomain> list = null;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.productlist.selectCategory");
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectCategory
	
	public List<ProductDomain> selectProductList(ProductRangeDTO prDTO) throws PersistenceException{
		List<ProductDomain> list = null;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		list = ss.selectList("kr.co.sist.user.productlist.selectProdcutList", prDTO);
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectCategory
	
	public int selectProductTotalCnt(ProductRangeDTO prDTO) throws PersistenceException{
		int cnt = 0;
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		cnt = ss.selectOne("kr.co.sist.user.productlist.selectProdcutCnt",prDTO);
		if(ss != null) {ss.close();}// end if
		return cnt;
	}// selectProductTotalCnt
	
	public void insertBookmark(BookmarkDTO bDTO)throws PersistenceException{
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		ss.insert("kr.co.sist.user.productlist.insertBookmark", bDTO);
		if(ss != null) {ss.close();}// end if
	}// insertBookmark
	
	public void deleteBookmark(BookmarkDTO bDTO)throws PersistenceException{
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		ss.delete("kr.co.sist.user.productlist.deleteBookmark", bDTO);
		if(ss != null) {ss.close();}// end if
	}// deleteBookmark
	
}// class
