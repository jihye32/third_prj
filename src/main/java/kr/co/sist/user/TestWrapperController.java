package kr.co.sist.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestWrapperController {

	@GetMapping("/wrapper")
	public String TestWrapper() {
		return "template/wrapper_test";
	}
}
