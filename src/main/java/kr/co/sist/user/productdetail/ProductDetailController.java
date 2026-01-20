package kr.co.sist.user.productdetail;

import org.springframework.stereotype.Controller;
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
	@GetMapping("/sell/type")
	public String openSellType() {
		return "sell/delivery_type :: deliveryType";
	}
	@GetMapping("/sell/payment")
	public String openSellFrm() {
		return "sell/payment_form :: paymentFrm";
	}
	@GetMapping("/sell/success")
	public String openSuccessFrm() {
		return "sell/sell_success :: sellSuccessFrm";
	}
	@GetMapping("/sell/fail")
	public String openFailFrm() {
		return "sell/sell_fail :: sellFailFrm";
	}
}
