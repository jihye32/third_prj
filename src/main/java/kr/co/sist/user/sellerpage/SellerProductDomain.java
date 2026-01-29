package kr.co.sist.user.sellerpage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SellerProductDomain {
	private String profile, intro; 
	private int sellProductCnt, reviewCnt;

}
