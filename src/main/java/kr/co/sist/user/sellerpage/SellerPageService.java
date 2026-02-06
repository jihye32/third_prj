package kr.co.sist.user.sellerpage;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerPageService {

	@Autowired(required = false)
	private SellerPageDAO spDAO;
	
	public SellerPageDomain searchSeller(int storeNum) {
		SellerPageDomain spd = null;
		try {
			spd = spDAO.selectSeller(storeNum);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end cathc
		return spd;
	}// searchSeller
	
	
}// class
