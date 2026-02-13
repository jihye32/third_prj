package kr.co.sist.user.mypage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReviewDTO {
	private String reviewerId, review;
	private int productNum, reviewRate, myStoreNum;
	private boolean receiveFlag, reviewAddFlag;

}
