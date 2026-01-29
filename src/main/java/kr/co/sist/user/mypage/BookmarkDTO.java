package kr.co.sist.user.mypage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BookmarkDTO {
	private String userId;
	private int productNum;
}
