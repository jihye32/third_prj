package kr.co.sist.user.sellerpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SellerPageController {
	@GetMapping("/sellerPage")
	public String sellerPage() {
		return "/seller_page/sellerPage";
	}
}// class
