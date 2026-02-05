package kr.co.sist.admin.login;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter@ToString
public class AdminLoginDomain {
	
	private String adminId;
	private String adminPass;
	private Date loginDate;

}
