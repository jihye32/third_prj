package kr.co.sist.user.mypage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MyPageDomain {
	private String nickName, selfText, profileImg; 
	private int reviewCnt, sellProductCnt; 
}
