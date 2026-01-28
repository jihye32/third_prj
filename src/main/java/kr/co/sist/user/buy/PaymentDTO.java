package kr.co.sist.user.buy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
	private int snum, pnum, charge;
	private String payment;
}
