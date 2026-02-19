package kr.co.sist.user.support;

import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/support")
@Controller
public class SupportController {

    private final SupportService service;

    public SupportController(SupportService service) {
        this.service = service;
    }

    // ===== 공지/FAQ =====
    @GetMapping("/supportFrm")
    public String supportFrm(
            @RequestParam(defaultValue = "notice") String tab,
            @RequestParam(required = false) String keyword,
            Model model
    ) throws SQLException {

        if ("ask".equals(tab)) {
          
            return "redirect:/support/askFrm";
        }

        String boardType = "notice".equals(tab) ? "N" : "F";
        List<SupportDomain> list = service.getList(boardType, keyword);

        model.addAttribute("tab", tab);
        model.addAttribute("boardType", boardType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("list", list);

        return "/support/supportFrm";
    }

    @GetMapping("/supportDetailFrm")
    public String supportDetailFrm(
            @RequestParam int boardNum,
            @RequestParam String boardType,
            Model model
    ) throws SQLException {

        SupportDomain detail = service.getDetail(boardNum, boardType);

        model.addAttribute("detail", detail);
        model.addAttribute("tab", "N".equals(boardType) ? "notice" : "faq");

        return "/support/supportDetailFrm";
    }

    // ===== Ask(1:1 문의) =====
    @GetMapping("/askFrm")
    public String askFrm(
            @RequestParam(required = false) String keyword, 
            HttpSession session, 
            Model model) throws Exception {
        
        String loginId = getLoginId(session);
        if (loginId == null) return "redirect:/user/login/loginFrm";

        // 검색어를 포함하여 리스트 조회
        List<UserAskDomain> list = service.getMyAskList(loginId, keyword); 
        
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword); // 검색창에 입력한 값 유지용
        
        return "/support/askFrm";
    }

    @GetMapping("/askDetailFrm")
    public String askDetailFrm(@RequestParam int askNum, HttpSession session, Model model) throws Exception {
        String loginId = getLoginId(session);
        if (loginId == null) return "redirect:/user/login/loginFrm";

        UserAskDomain detail = service.getMyAskDetail(loginId, askNum);
        model.addAttribute("detail", detail);
        return "/support/askDetailFrm";
    }

    @GetMapping("/askWriteFrm")
    public String askWriteFrm(HttpSession session) {
        String loginId = getLoginId(session);
        if (loginId == null) return "redirect:/user/login/loginFrm";
        return "/support/askWriteFrm";
    }

    @PostMapping("/askWriteProcess")
    public String askWriteProcess(
            @ModelAttribute UserAskDTO dto,
            HttpSession session,
            RedirectAttributes ra
    ) throws Exception {
        String loginId = getLoginId(session);
        if (loginId == null) return "redirect:/user/login/loginFrm";

        // ✅ 서버 유효성(공백 포함) 체크
        String title = dto.getAskTitle() == null ? "" : dto.getAskTitle().trim();
        String text  = dto.getAskText()  == null ? "" : dto.getAskText().trim();

        if (title.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "문의 제목을 입력해 주세요.");
            ra.addFlashAttribute("dto", dto); // 입력값 유지용(선택)
            return "redirect:/support/askWriteFrm";
        }
        if (text.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "문의 내용을 입력해 주세요.");
            ra.addFlashAttribute("dto", dto);
            return "redirect:/support/askWriteFrm";
        }

        service.writeAsk(loginId, dto);
        return "redirect:/support/askFrm";
    }

    @PostMapping("/askUpdateProcess")
    public String askUpdateProcess(
            @ModelAttribute UserAskDTO dto,
            HttpSession session,
            RedirectAttributes ra
    ) throws Exception {
        String loginId = getLoginId(session);
        if (loginId == null) return "redirect:/user/login/loginFrm";

        // ✅ 서버 유효성(공백 포함) 체크
        String title = dto.getAskTitle() == null ? "" : dto.getAskTitle().trim();
        String text  = dto.getAskText()  == null ? "" : dto.getAskText().trim();

        if (title.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "문의 제목을 입력해 주세요.");
            ra.addFlashAttribute("dto", dto);
            return "redirect:/support/askUpdateFrm?askNum=" + dto.getAskNum();
        }
        if (text.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "문의 내용을 입력해 주세요.");
            ra.addFlashAttribute("dto", dto);
            return "redirect:/support/askUpdateFrm?askNum=" + dto.getAskNum();
        }

        service.modifyAsk(loginId, dto);
        return "redirect:/support/askDetailFrm?askNum=" + dto.getAskNum();
    }

    
    @GetMapping("/askUpdateFrm")
    public String askUpdateFrm(@RequestParam int askNum, HttpSession session, Model model) throws Exception {
        String loginId = getLoginId(session);
        if (loginId == null) return "redirect:/user/login/loginFrm";

        UserAskDomain detail = service.getMyAskDetail(loginId, askNum);

        // 보안 체크
        if (detail == null || detail.getAnswerText() != null) {
            return "redirect:/support/askDetailFrm?askNum=" + askNum;
        }

        // ✅ 이미 detail 안에 imgList가 있음
        int imgCount = (detail.getImgList() == null) ? 0 : detail.getImgList().size();

        model.addAttribute("detail", detail);
        model.addAttribute("imgCount", imgCount);

        return "/support/askUpdateFrm";
    }

    
    @GetMapping("/testLogin")
    public String testLogin(HttpSession session,
                            @RequestParam(defaultValue = "user01") String userId) {
        session.setAttribute("uid", userId); // 우리가 쓰는 키
        return "redirect:/support/askFrm";
    }


    private String getLoginId(HttpSession session) {
        Object v1 = session.getAttribute("userId");
        if (v1 != null) return String.valueOf(v1);

        Object v2 = session.getAttribute("id");
        if (v2 != null) return String.valueOf(v2);

        Object v3 = session.getAttribute("uid");   // ✅ 로그인 컨트롤러와 키 맞추기
        if (v3 != null) return String.valueOf(v3);

        return null;
    }

}
