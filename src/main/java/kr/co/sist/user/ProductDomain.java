package kr.co.sist.user;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductDomain {
	private int productNum, sellStateCode, price;
	private String thumbnail, sellState, productName, tradingArea, bookmarkFlag, receiveFlag, timeString; 
	private double timDif;
	private Date lastModify;

}// class
