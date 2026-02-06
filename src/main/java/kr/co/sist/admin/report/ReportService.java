package kr.co.sist.admin.report;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportDAO rDAO;

    public ReportService(ReportDAO rDAO) {
        this.rDAO = rDAO;
    }

    public int getTotalCount(ReportDTO dto) {
        return rDAO.selectTotalCount(dto);
    }

    public List<ReportDomain> getReportList(ReportDTO dto) {
        return rDAO.selectReportList(dto);
    }

    public ReportDomain getReportDetail(int reportNum) {
        return rDAO.selectReportDetail(reportNum);
    }

    public List<String> getReportImages(int reportNum) {
        return rDAO.selectReportImages(reportNum);
    }

    public List<ReportDomain> getReportTypes() {
        return rDAO.selectReportTypes();
    }

    public List<ReportDomain> getReportStates() {
        return rDAO.selectReportStates();
    }

    public int processReport(ReportDTO dto) {
        if (dto.getReportStateCode() != null && dto.getReportStateCode() == 3) {
            if (dto.getAnswer() == null || dto.getAnswer().isBlank()) {
                throw new RuntimeException("처리완료는 처리 후 전달 내용이 필수입니다.");
            }
        }
        return rDAO.updateReportProcess(dto);
    }
}
