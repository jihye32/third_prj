package kr.co.sist.admin.product;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminProductController {

    @Autowired
    private AdminProductService ps;

    /**
     * 상품 관리 메인 페이지 (목록 조회, 검색, 정렬, 페이지네이션)
     */
    @GetMapping("/manager/product/product_main") 
    public String memberMainPage(AdminProductDTO pDTO, Model model) {
        // 1. 총 상품 수 조회 (검색 조건 포함)
        int totalCount = ps.getProductTotalCount(pDTO);
        
        // 2. 페이지네이션 설정 로직 (Board 프로젝트 기법 이식)
        int pageScale = ps.pageScale();
        int totalPage = ps.totalPage(totalCount, pageScale);
        int currentPage = pDTO.getCurrentPage();
        
        // 시작 번호와 끝 번호 계산
        int startNum = ps.startNum(currentPage, pageScale);
        int endNum = startNum + pageScale - 1;
        
        // DTO에 계산된 값 세팅
        pDTO.setStartNum(startNum);
        pDTO.setEndNum(endNum);
        pDTO.setTotalPage(totalPage);
        
        // 3. 데이터 조회 및 모델 추가
        List<AdminProductDomain> productList = ps.getProductList(pDTO);
        String pagination = ps.getPaginationHtml(pDTO); // Bootstrap 기반 페이지네이션 생성
        
        model.addAttribute("productList", productList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("totalCount", totalCount);
        
        return "manager/product/product_main"; 
    }

    /**
     * 상품 상세 관리 페이지 (정보 및 이미지 분리 조회)
     */
    @GetMapping("/manager/product/product_detail") 
    public String memberDetailPage(@RequestParam(defaultValue = "0") int ProductNo, Model model) {
        // 요구사항 반영: 상품 정보와 이미지를 각각 따로 조회하여 전달
        AdminProductDomain pd = ps.getProductDetail(ProductNo);
        List<String> productImages = ps.getProductImages(ProductNo);
        
        model.addAttribute("productDetail", pd);
        model.addAttribute("productImages", productImages);
        
        return "manager/product/product_detail"; 
    }
}