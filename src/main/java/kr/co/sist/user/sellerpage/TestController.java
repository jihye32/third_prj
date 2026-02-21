package kr.co.sist.user.sellerpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
	
	@Autowired
	SellerReviewService srs;

	 @GetMapping("/seller_page/review") // 이 주소가 브라우저 주소창과 일치해야 함!
	    public String review(@RequestParam("storeId") String storeId, Model model) {
	    	// templates/manage/login/login.html 파일을 찾아가게 함
		 model.addAttribute("countReview",srs.selectCountReview(storeId));
		 model.addAttribute("reviewStar",srs.selectReviewStar(storeId));
		 model.addAttribute("reviews",srs.selectReviews(storeId));
		 
	    	return "seller_page/wrapper_review :: fragmentWrapperReview";
	    }
    
	 @GetMapping("/test/testReview") // 이 주소가 브라우저 주소창과 일치해야 함!
	 public String testReview() {
		 // templates/manage/login/login.html 파일을 찾아가게 함
		 return "seller_page/testReview"; 
	 }
    @GetMapping("/report/testReport") // 이 주소가 브라우저 주소창과 일치해야 함!
    public String scamPage2() {
    	// templates/manage/login/login.html 파일을 찾아가게 함
    	return "report/testReport"; 
    }
    @GetMapping("/report/wrapper_report") // 이 주소가 브라우저 주소창과 일치해야 함!
    public String scamPage3() {
    	// templates/manage/login/login.html 파일을 찾아가게 함
    	return "report/wrapper_report :: fragmentReport";
    }
    @PostMapping("/report/wrapper_reportDone") // 이 주소가 브라우저 주소창과 일치해야 함!
    public String scamPage4(Model model) {
    	model.addAttribute("viewMode","done");
    	// templates/manage/login/login.html 파일을 찾아가게 함
    	return "report/wrapper_report :: fragmentScamComplete";
    }


}
