package kr.co.sist.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;; 

@Controller
public class MainController {
	@RequestMapping(value = "/", method = {GET, POST})
	public String testIndex(Model model, HttpSession session) {
		ProductDomain pd = null;
		List<ProductDomain> list = new ArrayList<ProductDomain>();
		for(int i =1; i<=6;i++) {
			pd = new ProductDomain("https://img2.joongna.com/media/original/2026/01/18/1768736344461BM3_LaDt3.jpg?impolicy=thumb&amp;size=150", "판매 상태 표시", "상품 제목", "거래 지역", 1, 1, 10000, null);
			pd.setProductName(pd.getProductName()+i);
			list.add(pd);
		}
		model.addAttribute("tempdata",list);
		return "index";
	}
}
