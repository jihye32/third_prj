package kr.co.sist.user.productlist;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;
 
@Repository
public class ProductListDAO {
	public List<CategoryDomain> selectCategory() throws PersistenceException{
		List<CategoryDomain> list = null;
		System.out.println("??????????"+list);
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		System.out.println("!!!!!!!!!!!"+list);
		list = ss.selectList("kr.co.sist.user.productlist.selectCategory");
		System.out.println("@@@@@@@@@@@"+list);
		System.out.println(list);
		
		if(ss != null) {ss.close();}// end if
		return list;
	}// selectCategory
}// class
