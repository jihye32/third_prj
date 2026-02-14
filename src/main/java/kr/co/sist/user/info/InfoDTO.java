package kr.co.sist.user.info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InfoDTO {

	private String id, pass, storeName, emailLocal, emailDomain, email;
	private String emailHash;
	
}// class
