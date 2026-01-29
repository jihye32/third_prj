package kr.co.sist.user.sellerpage;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SellerPageDomain {
	private String thumbnail, sellState, productName, tradingArea; 
	private int productNum, sellStateCode, price;
	private Date lastModify;

}
