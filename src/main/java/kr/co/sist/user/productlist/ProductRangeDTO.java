package kr.co.sist.user.productlist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRangeDTO {
	private String category, keyword, url, sortBy, userId;
	private int startNum, endNum, currentPage, totalPage, startPrice, endPrice;
}//class
