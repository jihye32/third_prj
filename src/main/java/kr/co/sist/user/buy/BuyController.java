package kr.co.sist.user.buy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import kr.co.sist.user.productdetail.enums.DealType;

@RequestMapping("/buy")
@Controller("UserBuyController")
public class BuyController {
	
	@Autowired
	private BuyService bs;
	
	@GetMapping("/{pnum}")
	public String openBuyType(@PathVariable Integer pnum, Model model) {
		BuyDomain bd = bs.searchProduct(pnum);
		if (bd == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	    }
		model.addAttribute("BuyDomain", bd);
		return "buy/delivery_type :: deliveryType";
	}
	
	@GetMapping("/payment/{pnum}")
	public String openPayment(@PathVariable int pnum, @RequestParam String type, Model model) {
		BuyDomain bd = bs.searchProduct(pnum);
		if (bd == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	    }
		model.addAttribute("type", type);
		model.addAttribute("BuyDomain", bd);
		return "buy/payment_form :: paymentFrm";
	}
	
	@GetMapping("/success/{orderId}")
	public String buySuccess(@PathVariable String orderId, Model model) {
		//거래 정보 가져오기
		PaymentDomain pd = bs.searchPayment(orderId);
		
		if (orderId == null || orderId.length() < 8) {
	        return orderId;
	    }

	    // UUID 뒤쪽 12자 사용
	    String base = orderId.substring(Math.max(0, orderId.length() - 12));
	    String id = "TN-" + base.toUpperCase(); // 약 15자
	    pd.setOrderId(id);
		
		model.addAttribute("PaymentDomain", pd);
		
		return "buy/buy_success :: sellSuccessFrm";
	}
	@GetMapping("/fail/{orderId}")
	public String buyFail(@PathVariable String orderId, @RequestParam String failMsg, Model model) {
		//orderId로는 주문 내역 삭제
		model.addAttribute("msg", failMsg);
		return "buy/buy_fail :: sellFailFrm";
	}
	
	@ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatus(ResponseStatusException ex) {

        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            return "redirect:/";
        }

        throw ex;
    }
	
}
