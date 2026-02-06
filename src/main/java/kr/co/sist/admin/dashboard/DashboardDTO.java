package kr.co.sist.admin.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DashboardDTO {
	
	private int joinToday;
	private int joinMonth;
	private int joinYear;
	
	private int visitToday;
	private int visitMonth;
	private int visitYear;
	
	private int soldToday;
	private int soldMonth;
	private int soldYear;
	
	private int uploadToday;
	private int purchaseToday;
	private int uploadMonth;
	private int purchaseMonth;
	private int uploadYear;
	private int purchaseYear;
	
	private String bestSeller;
	private int bestSellerAmount;
	
	private int tenUser;
	private int twentyUser;
	private int thirtyUser;
	private int fourtyUser;
	private int fiftyUser;
	private int sixtyUser;
	
	

}
