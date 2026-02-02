package kr.co.sist.user;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	private MainService ms;

	@RequestMapping(value = "/", method = {GET, POST})
	public String testIndex(Model model, HttpSession session) {
		int modelCnt = 1;
		List<ProductDomain> list = ms.searchMostViewProdcut();
		List<ProductDomain> temp = null;
		for (int i = 0; i < list.size(); i += 6) {
		    int end = Math.min(i + 6, list.size()); // 안전장치
		    temp = list.subList(i, end);

		    model.addAttribute("MostView" + modelCnt, temp);
		    modelCnt++;
		}// end for
		modelCnt=1;
		List<ProductDomain> list2 = ms.searchMostLikeProdcut();
		for (int i = 0; i < list2.size(); i += 6) {
			int end = Math.min(i + 6, list2.size()); // 안전장치
			temp = list2.subList(i, end);
			
			model.addAttribute("MostLike" + modelCnt, temp);
			modelCnt++;
		}// end for
		
//		session.setAttribute("uid", "1234");
		if(session.getAttribute("uid")!=null) {
			model.addAttribute("loginFlag", true);
		} else {
			model.addAttribute("loginFlag", false);
		}// end else
		
		return "index";
	}// testIndex
	
	
	// 나중에 Security 사용하면 수정해야할 method
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("uid");
		session.invalidate();
		return "redirect:/";
	}
	
}// class
