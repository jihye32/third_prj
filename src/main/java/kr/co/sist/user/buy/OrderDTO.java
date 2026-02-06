package kr.co.sist.user.buy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {
	private String orderId, buyerId,orderStatus,dealType;
	private int pnum, amount,charge;
}
