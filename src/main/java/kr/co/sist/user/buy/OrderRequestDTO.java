package kr.co.sist.user.buy;

import kr.co.sist.user.productdetail.enums.DealType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRequestDTO {
	private int pnum;
	private DealType dealType;
	private AddressDTO address;
}
