package kr.co.sist.user.buy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
	private int tel;
	private String orderId, name, addr, addrDetail;
}
