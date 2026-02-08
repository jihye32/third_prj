package kr.co.sist.user.sellerpage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.user.ProductDomain;

@RequestMapping("/store")
@Controller
public class SellerPageController {
	
	@Autowired
	private SellerPageService sps;
	
	@GetMapping("/{storeNum}")
	public String sellerPage(@PathVariable int storeNum, Model model, SellerPageRangeDTO sprDTO) {
		sprDTO.setMyId(5);
		sprDTO.setSellerId(storeNum);
		sprDTO.setStartNum(1);
		sprDTO.setEndNum(20);
		
		List<ProductDomain> allProduct = sps.searchAllProduct(sprDTO);// 판매하는 모든 물품 db에서 조회
		List<ProductDomain> sellingProduct = sps.findSelling(allProduct);// 조회된 모든 물품에서 판매중인 것만 선택
		List<ProductDomain> reserveProduct = sps.findReserve(allProduct);// 조회된 모든 물품에서 예약중인 것만 선택
		List<ProductDomain> selledProduct = sps.findSelled(allProduct);// 조회된 모든 물품에서 판매완료인 것만 선택
		
		
		model.addAttribute("SellerPageDomain",sps.searchSeller(storeNum));// 판매자 정보
		
		model.addAttribute("allProduct", allProduct);// 전체 물품 리스트
		model.addAttribute("allProductCnt", allProduct.size());// 전체 물품 수
		
		model.addAttribute("sellingProduct", sellingProduct);// 판매중인 물품 리스트
		model.addAttribute("sellingProductCnt", sellingProduct.size());// 판매중인 물품 수
		
		model.addAttribute("reserveProduct", reserveProduct);// 예약중인 물품 리스트
		model.addAttribute("reserveProductCnt", reserveProduct.size());// 예약중인 물품 수
		
		model.addAttribute("selledProduct", selledProduct);// 판매 완료된 물품 리스트
		model.addAttribute("selledProductCnt", selledProduct.size());// 판매 완료된 물품 리수
		
		System.out.println(selledProduct);
		
		return "/seller_page/sellerPage";
	}
}// class
