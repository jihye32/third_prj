package kr.co.sist.user.productlist;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.ProductDomain;

@Service
public class ProductListService {
	@Autowired(required = false)
	private ProductListDAO plDAO;

	public List<CategoryDomain> searchCategory() {
		List<CategoryDomain> list = null;
		try {
			list = plDAO.selectCategory();
		} catch (PersistenceException pe) {
		} // end catch
		return list;
	}// searchCategory
	
	public List<ProductDomain> searchProductList() {
		List<ProductDomain> list = null;
		try {
			list = plDAO.selectProductList();
		} catch (PersistenceException pe) {
		} // end catch
//		System.out.println(list);
		return list;
	}// searchProductList
	
	public int searchProductTotalCnt(ProductRangeDTO prDTO) {
		int cnt = 0;
		try {
			cnt = plDAO.selectProductTotalCnt(prDTO);
		} catch (PersistenceException pe) {
		} // end catch
		System.out.println(cnt);
		return cnt;
	}// searchProductTotalCnt
	
}// class
