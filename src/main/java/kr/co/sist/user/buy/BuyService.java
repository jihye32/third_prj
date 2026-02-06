package kr.co.sist.user.buy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserBuyService")
public class BuyService {

	@Autowired(required = false)
	BuyDAO bDAO;
	
	public BuyDomain searchProduct(int pnum) {
		BuyDomain bd= null;
		
		bd = bDAO.selectProduct(pnum);
		
		return bd;
	}
	public boolean addPaymentInfo(PaymentDTO pDTO) {
		return bDAO.insertPaymentInfo(pDTO)==1;
	}
}
