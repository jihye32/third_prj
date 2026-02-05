package kr.co.sist.user.buy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRequestDTO {
	private int pnum, tell;
	private String name, addr, addrDetail, paymentType;
}
