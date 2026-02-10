package kr.co.sist.admin.sales;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.dao.MyBatisHandler;


@Service
public class SalesService {
	@Autowired
	SalesDAO sDAO;
	
	public int selectSafepayToday() {
		return sDAO.selectSafepayToday();
	}
	public int selectSafepayYesterday() {
		return sDAO.selectSafepayYesterday();
	}
	public int selectSafepayMonth() {
		return sDAO.selectSafepayMonth();
	}
	
	
	public int selectChargeToday() {
		return sDAO.selectChargeToday();
	}
	public int selectChargeYesterday() {
		return sDAO.selectChargeYesterday();
	}
	public int selectChargeMonth() {
		return sDAO.selectChargeMonth();
	}
	
	
	public double selectCharge() {
		return sDAO.selectCharge();
	}
	
	public double insertCharge(Double chargeNow) {
		return sDAO.insertCharge(chargeNow);
	}
	
	
	//월별 매출 통계
	// DAO
	//안전결제 건 수
	public int selectSafepayMonthNow(String currentMonth) {
return sDAO.selectSafepayMonthNow(currentMonth);
	
	}
	public int selectSafepayLastMonth(String currentMonth) {
		return sDAO.selectSafepayLastMonth(currentMonth);
		
	}
	//스ㅜ수료
	public int selectChargeMonthNow(String currentMonth) {
		return sDAO.selectChargeMonthNow(currentMonth);
		
	}
	public int selectChargeLastMonth(String currentMonth) {
		return sDAO.selectChargeLastMonth(currentMonth);
		
	}

	
	
	
	public List<String> selectCategoryList() {
	    return sDAO.selectCategoryList();
	}//
	
	public int allPayMethod() {
		return sDAO.allPayMethod();
	}
	public int selectPayMethod() {
		return sDAO.selectPayMethod();
				}
	
	
	public List<Map<String, Object>>selectLastYearCharges(){
		return sDAO.selectLastYearCharges();
	}
	
	
}//class
