package kr.co.sist.user.sell;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sell")
@Controller("UserSellController")
public class SellController {

	@GetMapping("/sellFrm")
	public String sellFrm() {
		return "/sell/sellFrm";
	}// sellFrm
	
}
