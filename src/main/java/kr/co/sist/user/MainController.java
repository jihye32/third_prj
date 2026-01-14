package kr.co.sist.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;;

@Controller
public class MainController {
	@RequestMapping(value = "/", method = {GET, POST})
	public String testIndex() {
		return "index";
	}
}
