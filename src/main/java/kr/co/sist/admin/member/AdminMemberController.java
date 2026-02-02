package kr.co.sist.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // 추가됨
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminMemberController {

    // [중요] 이 부분이 빠져있어서 ms에 빨간 줄이 떴던 겁니다!
    @Autowired
    private AdminMemberService ms;

    @GetMapping("/manage/member/member_main") 
    public String memberMainPage(AdminMemberDTO mDTO, 
                                 @RequestParam(value="currentPage", defaultValue="1") int currentPage,
                                 Model model) {
        
        mDTO.setCurrentPage(currentPage);
        mDTO.setNumbers();

        // 이제 ms를 사용할 수 있습니다.
        int totalCount = ms.getTotalCount(mDTO);
        List<AdminMemberDomain> memberList = ms.getMemberList(mDTO);
        String pagination = ms.getPaginationHtml(mDTO, totalCount);
        
        model.addAttribute("memberList", memberList);
        model.addAttribute("pagination", pagination);
        
        return "manage/member/member_main"; 
    }
    

    @GetMapping("/manage/member/member_detail")
    public String memberDetailPage(@RequestParam String userId, Model model) {
        
        AdminMemberDetailDomain md = ms.getMemberDetail(userId);
        
        model.addAttribute("member", md);
        return "manage/member/member_detail";
    }

    // 2. 버튼 클릭 시 -> 다른 관리 페이지 (member_prdv)
    @GetMapping("/manage/member/member_prdv")
    public String memberPrdvPage(@RequestParam String userId, Model model) {
        // 필요한 데이터 조회 로직 (현재는 userId만 넘김)
        model.addAttribute("userId", userId);
        return "manage/member/member_prdv";
    }
    
    @GetMapping("/manage/member/member_product") 
    public String memberProductPage(Model model) {
        model.addAttribute("menu", "member"); 
        model.addAttribute("subMenu", "list");
        return "manage/member/member_product"; 
    }
}