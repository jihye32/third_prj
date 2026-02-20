package kr.co.sist.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminMemberController {

	
	/**
	 * 컨트롤러별 로그인 체크 (기존 AdminLoginController 설정에 맞춤)
	 */
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
	
    @Autowired
    private AdminMemberService ms;

    // 1. 회원관리 메인 리스트
    @GetMapping("/manage/member/member_main") 
    public String memberMainPage(AdminMemberDTO mDTO, 
                                 @RequestParam(value="currentPage", defaultValue="1") int currentPage,
                                 Model model) {
        mDTO.setCurrentPage(currentPage);
        mDTO.setNumbers();

        int totalCount = ms.getTotalCount(mDTO);
        List<AdminMemberDomain> memberList = ms.getMemberList(mDTO);
        String pagination = ms.getPaginationHtml(mDTO, totalCount);
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("pagination", pagination);
        return "manage/member/member_main"; 
    }

    // 2. 회원 기본 정보 상세 (member_detail)
    @GetMapping("/manage/member/member_detail")
    public String memberDetailPage(@RequestParam String userId, Model model) {
        AdminMemberDetailDomain md = ms.getMemberDetail(userId);
        model.addAttribute("member", md);
        return "manage/member/member_detail";
    }

    // 3. 상점 상세 관리 (member_prdv) - 모든 데이터 취합
    @GetMapping("/manage/member/member_prdv")
    public String memberPrdvPage(@RequestParam("userId") String userId,
                                 @RequestParam(value="sort", defaultValue="latest") String sort,
                                 @RequestParam(value="currentPage", defaultValue="1") int currentPage,
                                 Model model) {
        
        AdminMemberPrdvDomain store = ms.getStoreDetail(userId);
        
        int totalCount = ms.getStoreProductCount(userId);
        
        List<AdminMemberPrdvDomain> productList = ms.getStoreProducts(userId, sort, currentPage);
        
        List<AdminMemberPrdvDomain> reviewList = ms.getStoreReviews(userId);
        
        String pagination = ms.getPrdvPagination(userId, sort, currentPage, totalCount);

        model.addAttribute("store", store);
        model.addAttribute("productList", productList);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("userId", userId);
        model.addAttribute("sort", sort);
        
        return "manage/member/member_prdv";
    }

    @GetMapping("/manage/member/member_product") 
    public String memberProductPage(@RequestParam("userId") String userId,
                                    @RequestParam(value="type", defaultValue="purchase") String type,
                                    @RequestParam(value="currentPage", defaultValue="1") int currentPage,
                                    Model model) {
        
        AdminMemberProductDomain storeInfo = ms.getMemberStoreInfo(userId);
        
        int totalCount = ms.getHistoryCount(userId, type);
        
        List<AdminMemberProductDomain> historyList = ms.getMemberHistoryList(userId, type, currentPage);
        
        String pagination = ms.getHistoryPagination(userId, type, currentPage, totalCount);

        model.addAttribute("store", storeInfo); 
        model.addAttribute("historyList", historyList);
        model.addAttribute("type", type);
        model.addAttribute("userId", userId); 
        model.addAttribute("pagination", pagination);
        model.addAttribute("totalCount", totalCount);
        
        return "manage/member/member_product"; 
    }
    
    @PostMapping("/manage/member/delete")
    @ResponseBody 
    public ResponseEntity<String> deleteMember(@RequestParam("userId") String userId) {
        try {
            boolean isUpdated = ms.removeMember(userId);
            
            if (isUpdated) {
                return ResponseEntity.ok("Success");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    
    @PostMapping("/manage/member/suspend")
    @ResponseBody
    public ResponseEntity<String> suspendMember(@RequestParam("userId") String userId, 
                                                @RequestParam("action") String action,
                                                @RequestParam(value="days", defaultValue="0") int days) {
        try {
            boolean isUpdated = ms.modifySuspension(userId, action, days);
            
            if (isUpdated) return ResponseEntity.ok("Success");
            else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fail");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    
}