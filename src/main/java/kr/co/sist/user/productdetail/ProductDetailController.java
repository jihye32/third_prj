package kr.co.sist.user.productdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/product")
@Controller("UserProductDetailController")
public class ProductDetailController {

	@Autowired
	ProductDetailService ps;
	
	 // 1. 상세 페이지 조회
    @GetMapping("/{pnum}")
    public String searchProductDetail(@PathVariable int pnum, HttpServletRequest request , Model model) {
    	//서비스에서 상세정보 가져옴
    	ProductDetailDomain pdd = ps.searchProduct(pnum);
    	if(pdd == null) return "product/detail";//메인화면으로 이동시키기
    	SellerDomain sd = ps.searchSeller(pdd.getStore());
    	//
    	
    	HttpSession ss = request.getSession();
    	String store = (String)ss.getAttribute("storeName");
    	boolean myProductFlag = false;
        if(pdd.getStore().equals(store)) { //로그인한 사람이 작성한 글인지 확인
        	myProductFlag = true;
        	if(pdd.getSellStatusCode()==3) {
        		//결제번호 확인하고
        	}
        }else {
        	ps.modifyViewCnt(pnum);
        }
//        // [핵심 로직] 본인 상품 여부 확인 (Thymeleaf에서 버튼 분기 처리에 사용)
//        boolean isOwner = product.getSellerId().equals(principal.getName());
//        
//        model.addAttribute("product", product);
//        model.addAttribute("isOwner", isOwner);
        
        return "product/detail";
    }
//
//    // 2. 상품 상태 변경 (판매중/예약중/판매완료)
//    @PatchMapping("/{productId}/status")
//    @ResponseBody
//    public ResponseEntity<String> updateProductStatus(@PathVariable Long productId, @RequestParam String status) {
//        productService.updateStatus(productId, status);
//        return ResponseEntity.ok("Status updated");
//    }
//
//    // 3. 배송 완료 처리 (보라색 버튼)
//    @PostMapping("/{productId}/complete-shipping")
//    public String completeShipping(@PathVariable Long productId) {
//        productService.markAsShipped(productId);
//        return "redirect:/products/" + productId;
//    }
//
//    // 4. 상품 삭제
//    @DeleteMapping("/{productId}")
//    public String deleteProduct(@PathVariable Long productId) {
//        productService.delete(productId);
//        return "redirect:/products/list";
//    }
//	
}