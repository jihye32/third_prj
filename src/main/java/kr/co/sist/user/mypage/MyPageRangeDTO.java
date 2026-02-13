package kr.co.sist.user.mypage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MyPageRangeDTO {
	private String sortBy;
	private int startNum, endNum, page, myId,myStoreNum, searchCode, totalCnt;
}// class
