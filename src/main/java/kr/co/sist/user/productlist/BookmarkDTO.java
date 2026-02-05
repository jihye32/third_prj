package kr.co.sist.user.productlist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BookmarkDTO {
	private String userId;
	private int productNum, storeNum;
	private boolean resultFlag;
}
