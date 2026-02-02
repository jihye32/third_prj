package kr.co.sist.admin.member;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminMemberController {

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
        
        // 상점 프로필 정보
        AdminMemberPrdvDomain store = ms.getStoreDetail(userId);
        
        // 상품 페이징을 위한 전체 개수
        int totalCount = ms.getStoreProductCount(userId);
        
        // 현재 페이지/정렬에 맞는 상품 리스트 (10개)
        List<AdminMemberPrdvDomain> productList = ms.getStoreProducts(userId, sort, currentPage);
        
        // 상점 후기 전체 리스트
        List<AdminMemberPrdvDomain> reviewList = ms.getStoreReviews(userId);
        
        // prdv 전용 페이지네이션 HTML
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
        
        // 1. 상점 정보 조회 (Header용)
        AdminMemberProductDomain storeInfo = ms.getMemberStoreInfo(userId);
        
        // 2. 전체 내역 개수 조회 (페이징용)
        int totalCount = ms.getHistoryCount(userId, type);
        
        // 3. 페이징 처리된 내역 리스트 조회 (10개씩)
        List<AdminMemberProductDomain> historyList = ms.getMemberHistoryList(userId, type, currentPage);
        
        // 4. 전용 페이지네이션 HTML 생성
        String pagination = ms.getHistoryPagination(userId, type, currentPage, totalCount);

        // [중요] HTML의 변수명과 일치시킴
        model.addAttribute("store", storeInfo); 
        model.addAttribute("historyList", historyList);
        model.addAttribute("type", type);
        model.addAttribute("userId", userId); // 👈 탭 클릭 시 다시 사용하기 위해 필수!
        model.addAttribute("pagination", pagination);
        
        return "manage/member/member_product"; 
    }
}