package kr.co.sist.user.buy;

import kr.co.sist.user.buy.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {
	private String orderId, buyerId;
	private int pnum, amount,charge, dealTypeCode;
	private OrderStatus orderStatus;
}
