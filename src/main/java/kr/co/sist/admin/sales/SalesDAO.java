package kr.co.sist.admin.sales;

import java.util.List;

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
	public double selectCharge() {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectCharge");
		} finally {
			ss.close();
		}
	}
	
	
	//월별 매출 통계
	// DAO
	public int selectSafepayMonthNow(String safepayMonth) {
	    SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
	    try {
	        return ss.selectOne("kr.co.sist.admin.sales.selectSafepayMonthNow", safepayMonth);
	    } finally {
	        ss.close();
	    }
	}
	public int selectSafepayLastMonth(String safepayMonth) {
		SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		try {
			return ss.selectOne("kr.co.sist.admin.sales.selectSafepayLastMonth", safepayMonth);
		} finally {
			ss.close();
		}
	}
	
	
	
	public List<String> selectCategoryList() {
	    SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
	    try {
	        return ss.selectList("kr.co.sist.admin.sales.selectCategory");
	    } finally {
	        ss.close();
	    }
	}

}
