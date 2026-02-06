package kr.co.sist.user.buy;

import java.sql.Timestamp;

import kr.co.sist.user.buy.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
	private int amount;
	private String method,orderId,paymentKey,provider;
	private PaymentStatus paymentStatus;
	private Timestamp approvedDate;

}
