package kr.co.sist.user.buy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.buy.enums.OrderStatus;
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
			if(code == 1) {
				bd.setDealAddress(bDAO.selectAddress(pnum));
			}
		}
		bd.setDealType(type);
		return bd;
	}
	
	//주문 정보 저장
	public OrderDomain orderProduct(OrderRequestDTO orDTO, String buyerId) {
		BuyDomain bd= searchProduct(orDTO.getPnum());
		if(bd == null) return null;
		
		OrderDomain od = new OrderDomain();
		
		od.setOrderName(bd.getTitle());
		
		String orderId = "ORDER_"+UUID.randomUUID();
		od.setOrderId(orderId);
		
		int price = bd.getPrice();
		double chargeRate = bd.getChargeRate();
		
		BigDecimal priceBD = BigDecimal.valueOf(price);
		BigDecimal chargeRateBD = BigDecimal.valueOf(chargeRate);

		// 수수료 금액
		BigDecimal chargeAmountBD = priceBD
		    .multiply(chargeRateBD)
		    .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
		int charge = chargeAmountBD.intValue();

		// 총 결제 금액
		BigDecimal totalAmountBD = priceBD.add(chargeAmountBD);
		int amount = totalAmountBD.intValue();

		od.setAmount(amount);
		
		OrderDTO oDTO = new OrderDTO();
		oDTO.setAmount(amount);
		oDTO.setBuyerId(buyerId);
		oDTO.setCharge(charge);
		oDTO.setOrderId(orderId);
		oDTO.setPnum(orDTO.getPnum());
		oDTO.setOrderStatus(OrderStatus.READY.name());
		oDTO.setDealType(orDTO.getDealType().name());
		
		bDAO.insertOrderInfo(oDTO);
		
		AddressDTO aDTO = orDTO.getAddress();
		if(aDTO != null) {
			//배송지 추가
			aDTO.setOrderId(orderId);
			bDAO.insertAddress(aDTO);
		}
		
		return od;
	}
	
	//저장된 금액 가져옴
	public int searchOrderAmount(String orderId) {
		int amountInt = -1;
		Integer amount = bDAO.selectOrderAmount(orderId);
		if(amount != null) amountInt = amount.intValue();
		return amountInt;
	}
	
	//결제 정보 저장
	public boolean addPaymentInfo(PaymentDTO pDTO) {
		return bDAO.insertPaymentInfo(pDTO)==1;
	}
	
	//주문 상태 변경
	public boolean modifyOrderStaus(String orderId) {
		return bDAO.updateOrderStaus(orderId)==1;
	}
	
	//상품 번호 가져옴
	public int searchProductNum(String orderId) {
		int pnum = 0;
		Integer amount = bDAO.selectProductNum(orderId);
		if(amount != null) pnum = amount.intValue();
		return pnum;
	}
	//상품 상태 변경
	public boolean modifyProductStaus(int pnum) {
		return bDAO.updateProductStatus(pnum)==1;
	}
	
	//결제 완료 결과 가져오기
	public PaymentDomain searchPayment(String orderId) {
		PaymentDomain pd = bDAO.selectPayment(orderId);
		BuyDomain bd= bDAO.selectProduct(pd.getPnum());
		pd.setThumbnail(bd.getThumbnail());
		pd.setTitle(bd.getTitle());
		pd.setPrice(bd.getPrice());
		return pd;
	}
	
	public String searchSellerId(int pnum) {
		return bDAO.selectSellerId(pnum);
	}
	
	public AddressDTO searchAddress(String orderId) {
		return bDAO.selectAddress(orderId);
	}
	
}