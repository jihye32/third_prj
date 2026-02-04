package kr.co.sist.user.buy;

import java.sql.Date;

import kr.co.sist.user.buy.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
	private int amount;
	private String paymentType,orderId,paymentKey;
	private Date approvedDate;
	private PaymentStatus paymentStatus;
}
