package kr.co.sist.user.sell;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/sell")
@Controller("UserSellController")
public class SellController {

	@Autowired
	private SellService ss;
	private int productNum;
	private String thumbnailName;
	
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

	    if(ss.addProduct(sDTO)) {
	        result.put("success", true);
	        result.put("redirectUrl", "/myPage/myPageFrm");
	    } else {
	        result.put("success", false);
	        result.put("message", "등록 실패");
	    }// end else

	    return result;
	}// sellProcess
	
	@GetMapping("/modify/{productNum}")
	public String modifyProductFrm(Model model, HttpSession session ,SellDTO sDTO) {
//		System.out.println("productNum : " + productNum );
//		System.out.println("SellDTO : " + sDTO );// productNum 알아서 들어가네;;
		this.productNum = sDTO.getProductNum();
		int storeNum = 0; 
		if(session.getAttribute("snum") == null || "".equals(session.getAttribute("snum"))) {
			return "redirect:/user/login/loginFrm";
		}// end if
		storeNum = (Integer)session.getAttribute("snum");
		sDTO.setStoreNum(storeNum);
		
		SellDomain sellDomain = ss.searchModifyProdcut(sDTO);
		sellDomain.setProductNum(sDTO.getProductNum());
		thumbnailName = sellDomain.getThumbnail();
		
		System.out.println("sDTO : " + sDTO);
		System.out.println("조회된 데이터 : " + sellDomain);
		
		model.addAttribute("SellDomain", sellDomain);
		
		return "sell/modifySell";
	}// modifyProductFrm
	
	@ResponseBody
	@PostMapping("/modifyProcess")
	public Map<String, Object> modifyProcess(HttpSession session ,SellDTO sDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		sDTO.setProductNum(productNum);// 상품 번호 DTO에 저장
		this.productNum = sDTO.getProductNum();
		
		int storeNum = 0; 
		if(session.getAttribute("snum") == null || "".equals(session.getAttribute("snum"))) {
			 result.put("redirectUrl", "redirect:/user/login/loginFrm");
		}// end if
		storeNum = (Integer)session.getAttribute("snum");
		sDTO.setStoreNum(storeNum);
		if(thumbnailName != null && !"".equals(thumbnailName)) {
			sDTO.setThumbnailName(thumbnailName);
		}// end if
		
		if(ss.modifyProduct(sDTO)) {
	        result.put("success", true);
	        result.put("redirectUrl", "/product/"+sDTO.getProductNum());
	    } else {
	        result.put("success", false);
	        result.put("message", "등록 실패");
	    }// end else
		
		System.out.println("수정하기 sDTO : " + sDTO);
//		System.out.println("thumbnail : " + sDTO.getThumbnail());
//		System.out.println("thumbnail name : " + thumbnailName);
//		System.out.println("thumbnail null?? : " + sDTO.getThumbnail().isEmpty());
		
		
		return result;
	}// modifyProcess

	
}// class