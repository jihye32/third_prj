package kr.co.sist.user.mypage;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReviewDTO {
	private String reviewerId, reviewDetail, reviewerNickName;
	private Date reviewDate;

}
