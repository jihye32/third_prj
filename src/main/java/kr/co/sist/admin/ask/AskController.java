package kr.co.sist.admin.ask;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage/ask")
public class AskController {

    private final AskService as;

    public AskController(AskService as) {
        this.as = as;
    }

    // 문의 목록
    @GetMapping("/list")
    public String askList(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType,
            Model model) {

        AskDTO dto = new AskDTO();
        dto.setCurrentPage(currentPage);
        dto.setKeyword(keyword);
        dto.setSearchType(searchType == null ? "all" : searchType);

        return askListCommon(dto, model, "manage/ask/askList");
    }

    // 공통 목록 처리(페이지바 포함)
    private String askListCommon(AskDTO dto, Model model, String viewName) {

        int pageSize = 10;
        int blockSize = 5;

        dto.setStartRow((dto.getCurrentPage() - 1) * pageSize + 1);
        dto.setEndRow(dto.getCurrentPage() * pageSize);

        int totalCount = as.getTotalCount(dto);
        List<AskDomain> list = as.getAskList(dto);

        // 답변여부 문자열 세팅(선택)
        if (list != null) {
            for (AskDomain d : list) {
                d.setAnswerStatus(d.getAnswerText() == null || d.getAnswerText().isBlank() ? "미답변" : "답변완료");
            }
        }

        String baseUrl = "/manage/ask/list";
        String pagination = buildPageBar(baseUrl, dto, totalCount, pageSize, blockSize);

        model.addAttribute("pagination", pagination);
        model.addAttribute("list", list);
        model.addAttribute("dto", dto);
        model.addAttribute("totalCount", totalCount);

        model.addAttribute("menu", "customer");
        model.addAttribute("subMenu", "ask");

        return viewName;
    }

    // 상세
    @GetMapping("/detail")
    public String askDetail(@RequestParam int askNum, Model model) {
        // 1. 문의 본문 데이터 가져오기
        AskDomain ask = as.getAskDetail(askNum);
        
        // 2. 해당 문의에 등록된 이미지 파일명 리스트 가져오기
        List<String> imgList = as.getAskImages(askNum);

        model.addAttribute("ask", ask);
        model.addAttribute("imgList", imgList); // 리스트가 비어있어도 타임리프에서 처리 가능

        // 메뉴 활성화용 (기존 유지)
        model.addAttribute("menu", "customer");
        model.addAttribute("subMenu", "ask");

        return "manage/ask/askDetail";
    }

    // 답변 처리
    @PostMapping("/answerProcess")
    public String answerProcess(AskDTO dto) {

        as.answerAsk(dto);
        return "redirect:/manage/ask/detail?askNum=" + dto.getAskNum();
    }

    
    private String buildPageBar(String baseUrl, AskDTO dto, int totalCount, int pageSize, int blockSize) {

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        if (totalPage == 0) totalPage = 1;

        int currentPage = dto.getCurrentPage();
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
        int endPage = Math.min(startPage + blockSize - 1, totalPage);

        String searchType = dto.getSearchType();
        String keyword = dto.getKeyword();

        String extra = "";
        if (keyword != null && !keyword.isBlank()) {
            extra = "&searchType=" + searchType + "&keyword=" + keyword;
        }

        StringBuilder sb = new StringBuilder();

        if (startPage > 1) {
            sb.append("<a href='").append(baseUrl)
              .append("?currentPage=").append(startPage - 1)
              .append(extra).append("'>이전</a> ");
        }

        for (int p = startPage; p <= endPage; p++) {
            if (p == currentPage) {
                sb.append("<strong>").append(p).append("</strong> ");
            } else {
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
