package kr.co.sist.user.buy;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyDomain {
	private int pnum,price;
	private double chargeRate;
	private String thumbnail, title, dealAddr;
	private List<Integer> dealType;
}
