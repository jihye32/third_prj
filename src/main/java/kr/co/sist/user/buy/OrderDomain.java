package kr.co.sist.user.buy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDomain {
	private int amount;
	private String orderStatus;
}
