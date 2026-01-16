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
	@GetMapping("/sell/form")
	public String openSellFrm() {
		return "sell/product_sell :: sellFrm";
	}
}
