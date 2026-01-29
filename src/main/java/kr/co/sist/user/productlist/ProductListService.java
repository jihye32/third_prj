package kr.co.sist.user.productlist;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}// class
