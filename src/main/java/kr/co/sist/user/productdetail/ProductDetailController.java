package kr.co.sist.user.productdetail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductDetailController {

	@GetMapping("product/detail")
	public String testPage() {
		return "product_detail/sample";
	}
}
