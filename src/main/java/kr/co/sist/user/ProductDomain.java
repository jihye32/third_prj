package kr.co.sist.user;

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
	private String thumbnail, sellState, productName, tradingArea, bookmarkFlag, receiveFlag; 
	private int productNum, sellStateCode, price;
	private Date lastModify;

}// class
