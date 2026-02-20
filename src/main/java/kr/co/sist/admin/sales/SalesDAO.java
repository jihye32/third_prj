package kr.co.sist.admin.sales;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class SalesDAO {
	
	public int selectSafepayToday() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectSafepayToday");
		} finally {
			ss.close();
		}
	}
	public int selectSafepayYesterday() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectSafepayYesterday");
		} finally {
			ss.close();
		}
	}
	public int selectSafepayMonth() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectSafepayMonth");
		} finally {
			ss.close();
		}
	}
	public int selectChargeToday() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectChargeToday");
		} finally {
			ss.close();
		}
	}
	public int selectChargeYesterday() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectChargeYesterday");
		} finally {
			ss.close();
		}
	}
	public int selectChargeMonth() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectChargeMonth");
		} finally {
			ss.close();
		}
	}
	public double selectCharge() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectCharge");
		} finally {
			ss.close();
		}
	}//selectCharge
	public double insertCharge(Double chargeNow) {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
		try {
			return ss.insert("kr.co.sist.admin.sales.insertCharge",chargeNow);
		} finally {
			ss.close();
		}
	}//insertCharge
	
	
	
	
	
	
	//월별 매출 통계
	// DAO //안전결제
	public int selectSafepayMonthNow(String currentMonth) {
	    SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
	    try {
	        return ss.selectOne("kr.co.sist.admin.sales.selectSafepayMonthNow", currentMonth);
	    } finally {
	        ss.close();
	    }
	}
	public int selectSafepayLastMonth(String currentMonth) {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectSafepayLastMonth", currentMonth);
		} finally {
			ss.close();
		}
	}
	//수수료
	public int selectChargeMonthNow(String currentMonth) {
	    SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
	    try {
	        return ss.selectOne("kr.co.sist.admin.sales.selectChargeMonthNow", currentMonth);
	    } finally {
	        ss.close();
	    }
	}
	public int selectChargeLastMonth(String currentMonth) {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectChargeLastMonth", currentMonth);
		} finally {
			ss.close();
		}
	}
	
	
	//카테고리
	public List<String> selectCategoryList() {
	    SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
	    try {
	        return ss.selectList("kr.co.sist.admin.sales.selectCategory");
	    } finally {
	        ss.close();
	    }
	}//
	
	public int allPayMethod() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.allPayMethod");
		} finally {
			ss.close();
		}
	}
	public int selectPayMethod() {
		 SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		    try {
		        return ss.selectOne("kr.co.sist.admin.sales.selectPayMethod");
		    } finally {
		        ss.close();
		    }
	}
	
	
	public List<Map<String, Object>>selectLastYearCharges(){
		 SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		    try {
		        return ss.selectList("kr.co.sist.admin.sales.selectLastYearCharges");
		    } finally {
		        ss.close();
		    }
	}

}
