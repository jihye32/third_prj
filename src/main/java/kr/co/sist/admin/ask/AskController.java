package kr.co.sist.admin.ask;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage/ask")
public class AskController {

    private final AskService service;

    public AskController(AskService service) {
        this.service = service;
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

        int pageSize = 10;
        int blockSize = 5;

        dto.setStartRow((currentPage - 1) * pageSize + 1);
        dto.setEndRow(currentPage * pageSize);

        int totalCount = service.getTotalCount(dto);
        List<AskDomain> list = service.getAskList(dto);

        String pagination = buildPageBar("/manage/ask/list", dto, totalCount, pageSize, blockSize);

        model.addAttribute("list", list);
        model.addAttribute("dto", dto);
        model.addAttribute("pagination", pagination);
        model.addAttribute("menu", "customer");
        model.addAttribute("subMenu", "ask");

        return "manage/ask/askList";
    }

    // 문의 상세 + 답변
    @GetMapping("/detail")
    public String askDetail(@RequestParam int askNum, Model model) {
        model.addAttribute("detail", service.getAskDetail(askNum));
        return "manage/ask/askDetail";
    }

    // 답변 처리
    @PostMapping("/answer")
    public String answer(AskDTO dto) {
        service.answerAsk(dto);
        return "redirect:/manage/ask/detail?askNum=" + dto.getAskNum();
    }

    // 페이지바 (Board랑 동일)
    private String buildPageBar(String baseUrl, AskDTO dto, int totalCount, int pageSize, int blockSize) {

        int totalPage = (int)Math.ceil((double)totalCount / pageSize);
        int currentPage = dto.getCurrentPage();

        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
        int endPage = Math.min(startPage + blockSize - 1, totalPage);

        StringBuilder sb = new StringBuilder();

        for (int i = startPage; i <= endPage; i++) {
            if (i == currentPage) {
                sb.append("<strong>").append(i).append("</strong> ");
            } else {
                sb.append("<a href='").append(baseUrl)
                  .append("?currentPage=").append(i).append("'>")
                  .append(i).append("</a> ");
            }
        }
        return sb.toString();
    }
}
