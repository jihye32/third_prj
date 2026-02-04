package kr.co.sist.user.buy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.productdetail.enums.DealType;

@Service("UserBuyService")
public class BuyService {

	@Autowired(required = false)
	private BuyDAO bDAO;
	
	public BuyDomain searchProduct(int pnum) {
		BuyDomain bd= bDAO.selectProduct(pnum);
		if(bd == null) return null;
		BigDecimal big = bDAO.selectCharge();
		bd.setChargeRate(big.doubleValue());
		List<DealType> type = new ArrayList<DealType>();
		List<Integer> typeCode = bDAO.selectProductDealType(pnum);
		for(int code : typeCode) {
			type.add(DealType.fromCode(code));
		}
		bd.setDealType(type);
		
		return bd;
	}
	
	
	public boolean addPaymentInfo(PaymentDTO pDTO) {
		return bDAO.insertPaymentInfo(pDTO)==1;
	}
}
