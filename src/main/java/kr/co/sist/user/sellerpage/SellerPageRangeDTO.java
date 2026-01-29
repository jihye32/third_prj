package kr.co.sist.user.sellerpage;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SellerPageRangeDTO {
	private String sortBy, sellerId;
	private int page, size;
}
