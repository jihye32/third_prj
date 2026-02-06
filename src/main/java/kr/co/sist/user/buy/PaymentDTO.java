package kr.co.sist.user.buy;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
	private int amount;
	private String paymentTyp,orderId,paymentKey, paymentStatus;
	private Date approvedDate;
}
