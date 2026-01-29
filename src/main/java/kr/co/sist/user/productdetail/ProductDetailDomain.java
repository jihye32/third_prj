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
	private String category,title,content,thumbnail,dealAddr,sendFlag,productStatus;
	private int productNum,sellerCode,price,viewCnt,chatCnt,bookmarkCnt,sellStatusCode;
	private List<String> tag,img;
	private List<Integer> dealType;
	private Date upDate;
}