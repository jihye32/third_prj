package kr.co.sist.user.buy;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDomain {
	private int pnum, amount, charge, price;
	private String orderId, provider, method, paymentType, thumbnail, title, dealType;
	private Timestamp approvedDate;
}