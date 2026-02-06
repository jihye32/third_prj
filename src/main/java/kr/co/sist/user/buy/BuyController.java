package kr.co.sist.user.buy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/buy")
@Controller("UserBuyController")
public class BuyController {
	
	@Autowired
	BuyService bs;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/{pnum}")
	public String openBuyType(@PathVariable int pnum, Model model) {
		BuyDomain bd = new BuyDomain();
		List<Integer> sell = new ArrayList<Integer>();
		sell.add(1);
		sell.add(2);
		bd.setPnum(pnum);
		bd.setTitle("책책책책책책책책책");
		bd.setThumbnail("https://img2.joongna.com/media/original/2026/01/16/176855323874331j_7cv0Z.jpg?impolicy=resizeWatermark3&amp;ftext=%EC%9D%B8%EC%B2%9C%EA%B3%BC%EC%95%88%EC%82%B0%EC%82%AC%EC%9D%B4");
		bd.setDealType(sell);
		bd.setDealAddr("양재동");
		bd.setPrice(100000000);
		bd.setChargeRate(1);
		
		
//		BuyDomain bd = bs.searchProduct(pnum);

		model.addAttribute("BuyDomain", bd);
		
		return "/buy/delivery_type :: deliveryType";
	}
	@GetMapping("/payment/{pnum}")
	public String openPayment(@PathVariable int pnum, @RequestParam int type, Model model) {
		BuyDomain bd = new BuyDomain();
		List<Integer> sell = new ArrayList<Integer>();
		sell.add(1);
		sell.add(2);
		bd.setTitle("책책책책책책책책책");
		bd.setThumbnail("https://img2.joongna.com/media/original/2026/01/16/176855323874331j_7cv0Z.jpg?impolicy=resizeWatermark3&amp;ftext=%EC%9D%B8%EC%B2%9C%EA%B3%BC%EC%95%88%EC%82%B0%EC%82%AC%EC%9D%B4");
		bd.setDealType(sell);
		bd.setDealAddr("양재동");
		bd.setPrice(100);
		bd.setChargeRate(1);
		
		
//		BuyDomain bd = bs.searchProduct(pnum);

		model.addAttribute("type", type);
		model.addAttribute("BuyDomian", bd);
		return "/buy/payment_form :: paymentFrm";
	}
	
}
