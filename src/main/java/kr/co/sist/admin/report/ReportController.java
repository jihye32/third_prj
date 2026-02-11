package kr.co.sist.admin.report;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage/report")
public class ReportController {

    private final ReportService rs;

    public ReportController(ReportService rs) {
        this.rs = rs;
    }

    @GetMapping("/reportlist")
    public String reportList(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) Integer reportCode,
            @RequestParam(required = false) Integer reportStateCode,
            Model model) {

        ReportDTO dto = new ReportDTO();
        dto.setCurrentPage(currentPage);
        dto.setKeyword(keyword);
        dto.setSearchType(searchType == null ? "all" : searchType);
        dto.setReportCode(reportCode);
        dto.setReportStateCode(reportStateCode);

        return reportListCommon(dto, model, "manage/report/report_list");
    }

    private String reportListCommon(ReportDTO dto, Model model, String viewName) {

        int pageSize = 10;
        int blockSize = 5;

        dto.setStartRow((dto.getCurrentPage() - 1) * pageSize + 1);
        dto.setEndRow(dto.getCurrentPage() * pageSize);

        int totalCount = rs.getTotalCount(dto);
        List<ReportDomain> list = rs.getReportList(dto);

        String baseUrl = "/manage/report/reportlist";
        String pagination = buildPageBar(baseUrl, dto, totalCount, pageSize, blockSize);

        model.addAttribute("list", list);
        model.addAttribute("dto", dto);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pagination", pagination);

        model.addAttribute("typeList", rs.getReportTypes());
        model.addAttribute("stateList", rs.getReportStates());

        model.addAttribute("menu", "report");
        return viewName;
    }

    @GetMapping("/detail")
    public String reportDetail(@RequestParam int reportNum, Model model) {

        ReportDomain report = rs.getReportDetail(reportNum);
        List<String> imgList = rs.getReportImages(reportNum);

        model.addAttribute("report", report);
        model.addAttribute("imgList", imgList);

        model.addAttribute("stateList", rs.getReportStates());
        model.addAttribute("menu", "report");

        return "manage/report/report_detail";
    }

    @PostMapping("/reportprocess")
    public String reportProcess(ReportDTO dto) {
        rs.processReport(dto);
        return "redirect:/manage/report/detail?reportNum=" + dto.getReportNum() + "&saved=1";
    }

    private String buildPageBar(String baseUrl, ReportDTO dto, int totalCount, int pageSize, int blockSize) {

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        if (totalPage == 0) totalPage = 1;

        int currentPage = dto.getCurrentPage();
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
        int endPage = Math.min(startPage + blockSize - 1, totalPage);

        StringBuilder extra = new StringBuilder();

        if (dto.getReportCode() != null) extra.append("&reportCode=").append(dto.getReportCode());
        if (dto.getReportStateCode() != null) extra.append("&reportStateCode=").append(dto.getReportStateCode());

        if (dto.getKeyword() != null && !dto.getKeyword().isBlank()) {
            extra.append("&searchType=").append(dto.getSearchType())
                 .append("&keyword=").append(dto.getKeyword());
        }

        StringBuilder sb = new StringBuilder();

        if (startPage > 1) {
            sb.append("<a href='").append(baseUrl)
              .append("?currentPage=").append(startPage - 1)
              .append(extra).append("'>이전</a> ");
        }

        for (int p = startPage; p <= endPage; p++) {
            if (p == currentPage) sb.append("<strong>").append(p).append("</strong> ");
            else {
                sb.append("<a href='").append(baseUrl)
                  .append("?currentPage=").append(p)
                  .append(extra).append("'>").append(p).append("</a> ");
            }
        }

        if (endPage < totalPage) {
            sb.append("<a href='").append(baseUrl)
              .append("?currentPage=").append(endPage + 1)
              .append(extra).append("'>다음</a>");
        }

        return sb.toString();
    }
}
