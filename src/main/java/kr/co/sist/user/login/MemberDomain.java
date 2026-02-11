package kr.co.sist.user.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDomain {

	private String user_id, user_pass, delete_flag;
	private int store_num;
	
}// class
