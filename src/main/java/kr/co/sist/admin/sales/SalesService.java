package kr.co.sist.admin.sales;

import java.util.List;

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
	
	
	public double selectCharge() {
		return sDAO.selectCharge();
	}
	
	
	//월별 매출 통계
	// DAO
	public int selectSafepayMonthNow(String safepayMonth) {
return sDAO.selectSafepayMonthNow(safepayMonth);
	
	}
	public int selectSafepayLastMonth(String safepayMonth) {
		return sDAO.selectSafepayLastMonth(safepayMonth);
		
	}

	
	
	
	public List<String> selectCategoryList() {
	    return sDAO.selectCategoryList();
	}//
}//class
