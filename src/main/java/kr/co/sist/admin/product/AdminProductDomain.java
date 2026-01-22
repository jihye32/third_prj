package kr.co.sist.admin.product;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminProductDomain {
	
	private int product_num, sellStateCode, pro_price, deleteReason, deleteState;
	private String thumbnail, sellState, productName, tradingArea;
	private Date lastModify;
	
}
