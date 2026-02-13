package kr.co.sist.user.sell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/sell")
@Controller("UserSellController")
public class SellController {

	@Autowired
	private SellService ss;
	
	@GetMapping("/sellFrm")
	public String sellFrm(HttpSession session) {
		int storeNum = 0; 
		if(session.getAttribute("snum") == null || "".equals(session.getAttribute("snum"))) {
			return "redirect:/user/login/loginFrm";
		}// end if
		return "/sell/sellFrm";
	}// sellFrm
	
	@PostMapping("/sellProcess")
	public String sellProcess(SellDTO sDTO, HttpSession session) {
		int storeNum = 0; 
		if(session.getAttribute("snum") == null || "".equals(session.getAttribute("snum"))) {
			return "redirect:/user/login/loginFrm";
		}// end if
		storeNum = (Integer)session.getAttribute("snum");
		sDTO.setStoreNum(storeNum);// 내 상점 번호
		
		sDTO.setProductNum(ss.searchProductNum());// 신규 물품 번호 조회
		
		if(ss.addProdcut(sDTO)) {
			return "redirect:/myPage/myPageFrm";
		}// end if
		
		
		System.out.println(sDTO);
		return "/sell/sellErr";
	}// sellProcess
	
}// class
