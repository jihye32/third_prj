package kr.co.sist.user.info;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDomain {

	private String user_id, user_pass, name, store_name, email;
	private LocalDate birthday;
	
}// class
