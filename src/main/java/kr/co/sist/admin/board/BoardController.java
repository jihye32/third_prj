package kr.co.sist.admin.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage")
public class BoardController {

    private final BoardService bs;

    public BoardController(BoardService bs) {
        this.bs = bs;
    }

    // FAQ 목록
    @GetMapping("/faq/faq")
    public String faqList(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType,
            Model model) {

        BoardDTO dto = new BoardDTO();
        dto.setType("FAQ");
        dto.setBoardType("F");
        dto.setCurrentPage(currentPage);
        dto.setKeyword(keyword);
        dto.setSearchType(searchType == null ? "all" : searchType);

        return boardList(dto, model, "manage/faq/faq");
    }

    // 공지 목록
    @GetMapping("/notice/notice")
    public String noticeList(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType,
            Model model) {

        BoardDTO dto = new BoardDTO();
        dto.setType("NOTICE");
        dto.setBoardType("N");
        dto.setCurrentPage(currentPage);
        dto.setKeyword(keyword);
        dto.setSearchType(searchType == null ? "all" : searchType);

        return boardList(dto, model, "manage/notice/notice");
    }

    // 공통 목록 처리 
    private String boardList(BoardDTO dto, Model model, String viewName) {

        int pageSize = 10;
        dto.setStartRow((dto.getCurrentPage() - 1) * pageSize + 1);
        dto.setEndRow(dto.getCurrentPage() * pageSize);

        List<BoardDomain> list = bs.getBoardList(dto);

        model.addAttribute("list", list);
        model.addAttribute("dto", dto);
        model.addAttribute("menu", "customer");
        model.addAttribute("subMenu", dto.getType().equals("FAQ") ? "faq" : "notice");

        return viewName;
    }
}
