package kr.co.sist.user.productlist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRangeDTO {
	private String keyword, url, sortBy;
	private int startNum, endNum, totalPage, startPrice, endPrice, category, userId;
	private int currentPage=1;
}//class
