package kr.co.sist.user.sellerpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/store")
@Controller
public class SellerPageController {
	
	@Autowired
	private SellerPageService sps;
	
	@GetMapping("/{storeNum}")
	public String sellerPage(@PathVariable int storeNum, Model model) {
		System.out.println(sps.searchSeller(storeNum));
		model.addAttribute("temp",sps.searchSeller(storeNum));
		return "/seller_page/sellerPage";
	}
}// class
