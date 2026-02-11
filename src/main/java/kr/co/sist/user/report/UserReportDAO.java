package kr.co.sist.user.report;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


@Repository
public class UserReportDAO {
	
	public void insertReviewReport(SqlSession ss, UserReportDTO urDTO) {
	    ss.insert("kr.co.sist.user.report.insertReviewReport", urDTO);
	}

	public void insertReportImg(SqlSession ss, UserReportDTO urDTO) {
	    ss.insert("kr.co.sist.user.report.insertReportImg", urDTO);
	}

}
