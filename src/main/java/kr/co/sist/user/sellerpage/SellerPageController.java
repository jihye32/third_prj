package kr.co.sist.user.sellerpage;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.ProductDomain;

@RequestMapping("/store")
@Controller
public class SellerPageController {
	
	@Autowired
	private SellerPageService sps;
	
	@GetMapping("/{storeNum}")
	public String sellerPage(@PathVariable int storeNum, Model model, SellerPageRangeDTO sprDTO, HttpSession session) {
		
		if(session.getAttribute("snum") != null & !"".equals(session.getAttribute("snum"))) {
			sprDTO.setMyId((Integer)session.getAttribute("snum"));
		}// end if
		
		sprDTO.setSellerId(storeNum);
		sprDTO.setEndNum(20);
		sprDTO.setPage(1);

		int pageScale = sps.pageScale();
		int page = 1;
		int totalCnt = sps.totalCnt(sprDTO);
		int startNum = sps.startNum(page);
		int endNum = sps.endNum(page, totalCnt);
		sprDTO.setTotalCnt(totalCnt);
		sprDTO.setEndNum(endNum);// 시작은 1, 더보기 클릭시 page++
		sprDTO.setStartNum(startNum);
		boolean hasNext = endNum < totalCnt;
		
		
		List<ProductDomain> allProduct = sps.searchProductList(sprDTO);// 판매하는 모든 물품 db에서 조회
		List<ProductDomain> sellingProduct = sps.findSelling(allProduct);// 조회된 모든 물품에서 판매중인 것만 선택
		List<ProductDomain> reserveProduct = sps.findReserve(allProduct);// 조회된 모든 물품에서 예약중인 것만 선택
		List<ProductDomain> selledProduct = sps.findSelled(allProduct);// 조회된 모든 물품에서 판매완료인 것만 선택
		
		String storeId = sps.searchSellerId(storeNum);
		
		model.addAttribute("SellerPageDomain",sps.searchSeller(storeNum));// 판매자 정보
		model.addAttribute("storeNum",storeNum);// 판매자 정보
		model.addAttribute("storeId",storeId);// 판매자 정보
		
		model.addAttribute("allProduct", allProduct);// 전체 물품 리스트
		model.addAttribute("allProductCnt", totalCnt);// 전체 물품 수
		model.addAttribute("hasNext", hasNext);
		
		return "/seller_page/sellerPage";
	}//sellerPage
	
	@ResponseBody
	@PostMapping("/{storeId}/more")
	public JSONObject searchProduct(
			@PathVariable int storeId, 
			@RequestBody SellerPageRangeDTO sprDTO,
			HttpSession session
			){
		sprDTO.setSellerId(storeId);// 판매자 아이디 설정
		int pageScale = sps.pageScale();
		int page = sprDTO.getPage();
		int totalCnt = sps.totalCnt(sprDTO);
		int startNum = sps.startNum(page);
		int endNum = sps.endNum(page, totalCnt);
		sprDTO.setTotalCnt(totalCnt);
		sprDTO.setEndNum(endNum);// 시작은 1, 더보기 클릭시 page++
		sprDTO.setStartNum(startNum);
		if(session.getAttribute("snum") != null & !"".equals(session.getAttribute("snum"))) {
			sprDTO.setMyId((Integer)session.getAttribute("snum"));
		}// end if
		boolean hasNext = endNum < totalCnt;
		
		List<ProductDomain> list =sps.searchProductList(sprDTO);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("cnt", totalCnt);
		jsonObj.put("data", list);
		jsonObj.put("hasNext", hasNext);
		return jsonObj;
	}// searchProduct
}// class