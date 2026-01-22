package kr.co.sist.user.productdetail;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductModifyDTO {
	private String category,title,content,productStatus,thumbnail,dealAddr;
	private int product_num,price;
	private List<String> tag,img,dealType;
}