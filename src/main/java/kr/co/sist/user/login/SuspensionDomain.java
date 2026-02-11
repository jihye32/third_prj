package kr.co.sist.user.login;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SuspensionDomain {

	private String user_id, flag;
	private LocalDateTime end_date;
	
}// class
