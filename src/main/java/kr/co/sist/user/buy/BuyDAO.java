package kr.co.sist.user.buy;

import org.springframework.stereotype.Component;

@Component("UserBuyDAO")
public class BuyDAO {

	public BuyDomain selectProduct(int pnum) {
		BuyDomain bd= null;
		
		return bd;
	}
	public int insertPaymentInfo(PaymentDTO pDTO) {
		int cnt = 0;
		
		return cnt;
	}
}
