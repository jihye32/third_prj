package kr.co.sist.user.sell;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sell")
@Controller
public class SellController {
	@GetMapping("/type")
	public String openSellType() {
		return "sell/delivery_type :: deliveryType";
	}
	@GetMapping("/payment")
	public String openSellFrm() {
		return "sell/payment_form :: paymentFrm";
	}
	@GetMapping("/success")
	public String openSuccessFrm() {
		return "sell/sell_success :: sellSuccessFrm";
	}
	@GetMapping("/fail")
	public String openFailFrm() {
		return "sell/sell_fail :: sellFailFrm";
	}
}
