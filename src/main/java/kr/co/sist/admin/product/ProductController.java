package kr.co.sist.admin.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

	@GetMapping("/manage/product/product_main") 
	public String memberMainPage(Model model) {
	    return "manage/product/product_main"; 
	}
}