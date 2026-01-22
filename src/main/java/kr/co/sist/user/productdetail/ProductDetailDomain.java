package kr.co.sist.user.productdetail;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDetailDomain {
	private String category,store,title,content,productStatus,thumbnail,dealAddr,sellStatus;
	private int product_num,price,viewCnt,chatCnt,bookmarkCnt;
	private List<String> tag,img,dealType;
	private Date upDate;
}