package kr.co.sist.user.mypage;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class ProductDomain {
	private String thumbnail, sellState, productName, tradingArea, receiveFlag; 
	private int productNum, sellStateCode, price;
	private Date lastModify;

}// class
