package kr.co.sist.user.sell;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public Map<String, Object> sellProcess(SellDTO sDTO, HttpSession session) {

	    Map<String, Object> result = new HashMap<String, Object>();

	    if(session.getAttribute("snum") == null) {
	        result.put("success", false);
	        result.put("message", "로그인이 필요합니다.");
	        return result;
	    }// end if

	    int storeNum = (Integer)session.getAttribute("snum");
	    sDTO.setStoreNum(storeNum);
	    sDTO.setProductNum(ss.searchProductNum());

	    if(ss.addProdcut(sDTO)) {
	        result.put("success", true);
	        result.put("redirectUrl", "/myPage/myPageFrm");
	    } else {
	        result.put("success", false);
	        result.put("message", "등록 실패");
	    }// end else

	    return result;
	}// sellProcess

	
}// class