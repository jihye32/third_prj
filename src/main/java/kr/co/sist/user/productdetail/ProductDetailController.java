package kr.co.sist.user.productdetail;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductDetailController {

	@GetMapping("/product/detail")
	public String testPage() {
		return "product_detail/product_detail";
	}
	@GetMapping("/chat/list")
	public String testChat() {
		return "template/wrapper_frm :: chatListForm";
	}
	@GetMapping("/report/form")
	public String testReport(Model model) {
		
		
		//service, DAO 작업하는 곳
		
		
		//iframe 디자인을 작업한 폴더명/iframe 디자인을 작업한 html명 :: 불러올 fragment명
		return "pruduct_detail/frm :: deliveryForm";
	}
}
