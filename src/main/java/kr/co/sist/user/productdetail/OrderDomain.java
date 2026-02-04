package kr.co.sist.user.productdetail;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDomain {
	private String orderId;
	private Date deliveredDate;
}
