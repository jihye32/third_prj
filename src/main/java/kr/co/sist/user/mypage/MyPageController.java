package kr.co.sist.user.mypage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.sist.user.ProductDomain;

@RequestMapping("/myPage")
@Controller
public class MyPageController {
	
	@GetMapping("/myPageFrm")
	public String myPageFrm(Model model) {
		// 가데이터용
//		ProductDomain pd = null;
//		List<ProductDomain> list = new ArrayList<ProductDomain>();
//		for(int i =1; i<=6;i++) {
////			pd = new ProductDomain("https://img2.joongna.com/media/original/2026/01/18/1768736344461BM3_LaDt3.jpg?impolicy=thumb&amp;size=150", "판매 상태 표시", "판매 상품 제목", "거래 지역", null, null, 1, 1, 10000, i, null);
//			pd.setProductName(pd.getProductName()+i);
//			list.add(pd);
//		}
//		if(new Random().nextBoolean()) {
//			model.addAttribute("tempData", list);
//		}// end if
//		
//		
//		List<ProductDomain> list2 = new ArrayList<ProductDomain>();
//		for(int i =1; i<=6;i++) {
////			pd = new ProductDomain("https://img2.joongna.com/media/original/2026/01/18/1768736344461BM3_LaDt3.jpg?impolicy=thumb&amp;size=150", "판매 상태 표시", "찜 상품 제목", "거래 지역", null, null, 1, 1, 10000, i, null);
//			pd.setProductName(pd.getProductName()+i);
//			list2.add(pd);
//		}
//		if(new Random().nextBoolean()) {
//			model.addAttribute("tempData2", list2);
//		}// end if
		model.addAttribute("info", "");
		return "/mypage/myPageFrm";
	}// myPageFrm 
	
	@GetMapping("/myPageFrm/purchased")
	public String purchased() {
		return "myPage/wrapper_purchased :: fragmentPurchased";
	}// purchased
	
}// class
