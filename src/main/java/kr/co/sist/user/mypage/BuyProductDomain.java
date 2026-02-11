package kr.co.sist.user.mypage;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BuyProductDomain {
	private String productName, thumbnail, review;
	private int productNum, price;
	private Date createdDate, deliverdDate;
}// class
