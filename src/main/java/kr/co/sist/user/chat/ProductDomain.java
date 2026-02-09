package kr.co.sist.user.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDomain {
	private String title, thumbnail, sellerId;
	private int price;
}
