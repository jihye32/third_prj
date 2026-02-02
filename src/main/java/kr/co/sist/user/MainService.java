package kr.co.sist.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

	@Autowired
	private MainDAO mDAO;

	public List<ProductDomain> searchMostViewProdcut() {
		List<ProductDomain> list = null;
		try {
			list = mDAO.selectMostViewProdcut();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		return list;
	}// selectList
	
	public List<ProductDomain> searchMostLikeProdcut() {
		List<ProductDomain> list = null;
		try {
			list = mDAO.selectMostLikeProdcut();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		return list;
	}// selectList
}// class
