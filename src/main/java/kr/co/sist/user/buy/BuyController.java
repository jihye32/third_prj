package kr.co.sist.user.buy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.sist.user.productdetail.enums.DealType;

@RequestMapping("/buy")
@Controller("UserBuyController")
public class BuyController {
	
	@Autowired
	private BuyService bs;
	
	@GetMapping("/{pnum}")
	public String openBuyType(@PathVariable int pnum, Model model) {
		BuyDomain bd = bs.searchProduct(pnum);
		if(bd==null)return "redirect: /";
		model.addAttribute("BuyDomain", bd);
		model.addAttribute("DealType", DealType.class);
		
		return "/buy/delivery_type :: deliveryType";
	}
	
	@GetMapping("/payment/{pnum}")
	public String openPayment(@PathVariable int pnum, @RequestParam String type, Model model) {
		BuyDomain bd = bs.searchProduct(pnum);
		if(bd==null)return "redirect: /";

		model.addAttribute("type", type);
		model.addAttribute("BuyDomain", bd);
		return "/buy/payment_form :: paymentFrm";
	}
	
}
