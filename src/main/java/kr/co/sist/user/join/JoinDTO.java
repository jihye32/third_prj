package kr.co.sist.user.join;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinDTO {

	private String id, pass, userName, storeName, emailLocal, emailDomain, email, ip;
	private int storeNum;
	private LocalDate birth;
	
}// class
