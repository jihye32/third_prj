package kr.co.sist.user.findAccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindAccountDTO {

	private String id, tempPass, name, email;
	private String nameHash, emailHash;
	
}// class
