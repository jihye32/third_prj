package kr.co.sist.user.sellerpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	

	 @GetMapping("/seller_page/review") // 이 주소가 브라우저 주소창과 일치해야 함!
	    public String review() {
	    	// templates/manage/login/login.html 파일을 찾아가게 함
	    	return "/seller_page/wrapper_review :: fragmentWrapperReview";
	    }
    
	 @GetMapping("/test/testReview") // 이 주소가 브라우저 주소창과 일치해야 함!
	 public String testReview() {
		 // templates/manage/login/login.html 파일을 찾아가게 함
		 return "/seller_page/testReview"; 
	 }
    @GetMapping("/test/testReport") // 이 주소가 브라우저 주소창과 일치해야 함!
    public String scamPage2() {
    	// templates/manage/login/login.html 파일을 찾아가게 함
    	return "/report/testReport"; 
    }
    @GetMapping("/test/testReport/wrapper") // 이 주소가 브라우저 주소창과 일치해야 함!
    public String scamPage3() {
    	// templates/manage/login/login.html 파일을 찾아가게 함
    	return "/report/wrapper_report :: fragmentReport";
    }


}
