package kr.co.sist.admin.report;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import kr.co.sist.dao.MyBatisHandler;

@Repository
public class ReportDAO {

    public int selectTotalCount(ReportDTO dto) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectOne("kr.co.sist.admin.report.selectTotalCount", dto);
        }
    }

    public List<ReportDomain> selectReportList(ReportDTO dto) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectList("kr.co.sist.admin.report.selectReportList", dto);
        }
    }

    public ReportDomain selectReportDetail(int reportNum) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectOne("kr.co.sist.admin.report.selectReportDetail", reportNum);
        }
    }

    public List<String> selectReportImages(int reportNum) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectList("kr.co.sist.admin.report.selectReportImages", reportNum);
        }
    }

    // ✅ 신고유형 드롭다운(ReportDomain 재사용)
    public List<ReportDomain> selectReportTypes() {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectList("kr.co.sist.admin.report.selectReportTypes");
        }
    }

    // ✅ 처리상태 드롭다운(ReportDomain 재사용)
    public List<ReportDomain> selectReportStates() {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false)) {
            return ss.selectList("kr.co.sist.admin.report.selectReportStates");
        }
    }

    public int updateReportProcess(ReportDTO dto) {
        try (SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true)) {
            return ss.update("kr.co.sist.admin.report.updateReportProcess", dto);
        }
    }
}
