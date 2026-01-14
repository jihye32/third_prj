package kr.co.sist.user.sell;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
	
	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String testIndex() {
		return "index";
	}
	
	@RequestMapping(value = "/header", method = {RequestMethod.GET, RequestMethod.POST})
	public String testHeader() {
		return "template/header";
	}
	@RequestMapping(value = "/footer", method = {RequestMethod.GET, RequestMethod.POST})
	public String testFooter() {
		return "template/footer";
	}
}
