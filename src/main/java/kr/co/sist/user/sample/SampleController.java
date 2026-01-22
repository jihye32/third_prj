package kr.co.sist.user.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("UserSampleController")
public class SampleController {

	@GetMapping("/test/main")
	public String sampleMain() {
		return "/sample/main_sample";
	}
	
	@GetMapping("/test")
	public String sampleTest() {
		return "sample/wrapper_sample :: fragmentSample";
	}
}
