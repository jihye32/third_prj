package kr.co.sist.user.buy;

import java.util.List;

import kr.co.sist.user.productdetail.enums.DealType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyDomain {
	private int pnum,price;
	private double chargeRate;
	private String thumbnail, title, dealAddress;
	private List<DealType> dealType;
}
