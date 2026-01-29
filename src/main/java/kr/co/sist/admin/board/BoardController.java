package kr.co.sist.admin.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

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
    
    // ============================================================
    // 추가 1) 작성 폼 (FAQ)
    // ============================================================
    @GetMapping("/faq/writeFrm")
    public String faqWriteFrm(Model model) {
        BoardDTO dto = new BoardDTO();
        dto.setType("FAQ");
        dto.setBoardType("F");

        model.addAttribute("dto", dto);
        model.addAttribute("mode", "create"); // create | edit
        model.addAttribute("menu", "customer");
        model.addAttribute("subMenu", "faq");

        return "manage/board/boardForm";
    }

    // 추가 2) 작성 처리 (FAQ)
    @PostMapping("/faq/writeProcess")
    public String faqWriteProcess(BoardDTO dto, HttpSession session) {
        dto.setType("FAQ");
        dto.setBoardType("F");
        //dto.setAdminId(getAdminId(session)); // 세션에서 관리자 아이디

        dto.setAdminId("admin");//임시 관리자 아이디
        bs.addBoard(dto);

        return "redirect:/manage/faq/faq";
    }

    // ============================================================
    // 추가 3) 작성 폼 (공지)
    // ============================================================
    @GetMapping("/notice/writeFrm")
    public String noticeWriteFrm(Model model) {
        BoardDTO dto = new BoardDTO();
        dto.setType("NOTICE");
        dto.setBoardType("N");

        model.addAttribute("dto", dto);
        model.addAttribute("mode", "create");
        model.addAttribute("menu", "customer");
        model.addAttribute("subMenu", "notice");

        return "manage/board/boardForm";
    }

    // 추가 4) 작성 처리 (공지)
    @PostMapping("/notice/writeProcess")
    public String noticeWriteProcess(BoardDTO dto, HttpSession session) {
        dto.setType("NOTICE");
        dto.setBoardType("N");
        //dto.setAdminId(getAdminId(session));
        dto.setAdminId("admin");//임시 관리자 아이디

        bs.addBoard(dto);

        return "redirect:/manage/notice/notice";
    }
    
    @GetMapping("/faq/modifyFrm")
    public String faqModifyFrm(
            @RequestParam int boardNum,
            Model model) {

        BoardDTO param = new BoardDTO();
        param.setBoardNum(boardNum);

        BoardDomain detail = bs.getBoardDetail(boardNum);

        BoardDTO dto = new BoardDTO();
        dto.setBoardNum(detail.getBoardNum());
        dto.setTitle(detail.getTitle());
        dto.setContent(detail.getContent());
        dto.setBoardType("F");
        dto.setType("FAQ");

        model.addAttribute("dto", dto);
        model.addAttribute("mode", "edit");

        return "manage/board/boardForm";
    }
    
    @GetMapping("/notice/modifyFrm")
    public String noticeModifyFrm(
            @RequestParam int boardNum,
            Model model) {

        BoardDTO param = new BoardDTO();
        param.setBoardNum(boardNum);

        BoardDomain detail = bs.getBoardDetail(boardNum);

        BoardDTO dto = new BoardDTO();
        dto.setBoardNum(detail.getBoardNum());
        dto.setTitle(detail.getTitle());
        dto.setContent(detail.getContent());
        dto.setBoardType("N");
        dto.setType("NOTICE");

        model.addAttribute("dto", dto);
        model.addAttribute("mode", "edit");

        return "manage/board/boardForm";
    }
    
    @PostMapping("/faq/modifyProcess")
    public String faqModifyProcess(BoardDTO dto, HttpSession session) {

        dto.setBoardType("F");
        dto.setType("FAQ");
        dto.setAdminId((String)session.getAttribute("adminId"));

        bs.modifyBoard(dto);

        return "redirect:/manage/faq/faq";
    }
    
    @PostMapping("/notice/modifyProcess")
    public String noticeModifyProcess(BoardDTO dto, HttpSession session) {

        dto.setBoardType("N");
        dto.setType("NOTICE");
        dto.setAdminId((String)session.getAttribute("adminId"));

        bs.modifyBoard(dto);

        return "redirect:/manage/notice/notice";
    }
}
