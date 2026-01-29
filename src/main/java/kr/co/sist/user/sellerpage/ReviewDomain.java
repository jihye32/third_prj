package kr.co.sist.user.sellerpage;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReviewDomain {
	private String reviewerId, reviewDetail, reviewerNickName;
	private Date reviewDate;

}
