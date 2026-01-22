package kr.co.sist.admin.product;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class AdminProductDTO {
	
	private int category, page, sell_num, product_num;
	private int startNum;
    private int endNum;
    private int currentPage = 1;
    private int totalPage;
	private String sellerId, buyerId, img, name,
	price, description, condition, option, area;
	private String searchField; // 검색 조건
    private String keyword;     // 검색어
    private String sortBy;      // 정렬 기준 (최신순, 가격순 등)
	private Date input_date;

	
}
