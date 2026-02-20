package kr.co.sist.admin.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage")
public class BoardController {

    private final BoardService bs;

    public BoardController(BoardService bs) {
        this.bs = bs;
    }
    
    
    
    private String getAdminId(HttpSession session) {
        Object v = session.getAttribute("loginAdmin");
        return v == null ? null : v.toString();
    }
    
    @ModelAttribute
    public void checkAdminLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        if (session == null || session.getAttribute("loginAdmin") == null) {
            
            String loginUrl = request.getContextPath() + "/manage";
            
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                response.sendRedirect(loginUrl);
            }
        }
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

        int pageSize = 10;   // 한 페이지 글 수
        int blockSize = 5;   // 페이지 번호 묶음(1~5, 6~10 ...)

        // startRow/endRow 계산
        dto.setStartRow((dto.getCurrentPage() - 1) * pageSize + 1);
        dto.setEndRow(dto.getCurrentPage() * pageSize);

        // 전체 건수 + 목록
        int totalCount = bs.getTotalCount(dto);
        List<BoardDomain> list = bs.getBoardList(dto);

        // 페이지바 HTML 생성 (검색 유지 포함)
        String baseUrl = dto.getType().equals("FAQ")
                ? "/manage/faq/faq"
                : "/manage/notice/notice";

        String pagination = buildPageBar(baseUrl, dto, totalCount, pageSize, blockSize);
        model.addAttribute("pagination", pagination); // ✅ 템플릿이 쓰는 이름

        model.addAttribute("list", list);
        model.addAttribute("dto", dto);
        model.addAttribute("totalCount", totalCount);

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
        dto.setAdminId(getAdminId(session)); // 세션에서 관리자 아이디

//        String adminId = getAdminId(session);   
//        dto.setAdminId(adminId);
        bs.addBoard(dto);
        System.out.println("adminId = " + dto.getAdminId());

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
        dto.setAdminId(getAdminId(session));
//        String adminId = getAdminId(session);   
//        dto.setAdminId(adminId);

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
        dto.setAdminId((String)session.getAttribute("loginAdmin"));

//        String adminId = getAdminId(session);   
//        dto.setAdminId(adminId);
        bs.modifyBoard(dto);

        return "redirect:/manage/faq/faq";
    }
    
    @PostMapping("/notice/modifyProcess")
    public String noticeModifyProcess(BoardDTO dto, HttpSession session) {

        dto.setBoardType("N");
        dto.setType("NOTICE");
        dto.setAdminId((String)session.getAttribute("loginAdmin"));

//        String adminId = getAdminId(session);   
//        dto.setAdminId(adminId);
        bs.modifyBoard(dto);

        return "redirect:/manage/notice/notice";
    }
    
 // FAQ 삭제(논리삭제)
    @PostMapping("/faq/deleteProcess")
    public String faqDeleteProcess(@RequestParam int boardNum) {
        bs.removeBoard(boardNum);     // delete_flag = 'Y'
        return "redirect:/manage/faq/faq";
    }

    // 공지 삭제(논리삭제)
    @PostMapping("/notice/deleteProcess")
    public String noticeDeleteProcess(@RequestParam int boardNum) {
        bs.removeBoard(boardNum);
        return "redirect:/manage/notice/notice";
    }
    
    //페이지네이션
    private String buildPageBar(String baseUrl, BoardDTO dto, int totalCount, int pageSize, int blockSize) {

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        if (totalPage == 0) totalPage = 1;

        int currentPage = dto.getCurrentPage();
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
        int endPage = Math.min(startPage + blockSize - 1, totalPage);

        String searchType = dto.getSearchType();
        String keyword = dto.getKeyword();

        // 검색 파라미터 유지
        String extra = "";
        if (keyword != null && !keyword.isBlank()) {
            extra = "&searchType=" + searchType + "&keyword=" + keyword;
        }

        StringBuilder sb = new StringBuilder();

        // 이전 블록
        if (startPage > 1) {
            sb.append("<a href='").append(baseUrl)
              .append("?currentPage=").append(startPage - 1)
              .append(extra).append("'>이전</a> ");
        }

        // 페이지 번호
        for (int p = startPage; p <= endPage; p++) {
            if (p == currentPage) {
                sb.append("<strong>").append(p).append("</strong> ");
            } else {
                sb.append("<a href='").append(baseUrl)
                  .append("?currentPage=").append(p)
                  .append(extra).append("'>").append(p).append("</a> ");
            }
        }

        // 다음 블록
        if (endPage < totalPage) {
            sb.append("<a href='").append(baseUrl)
              .append("?currentPage=").append(endPage + 1)
              .append(extra).append("'>다음</a>");
        }

        return sb.toString();
    }
}
