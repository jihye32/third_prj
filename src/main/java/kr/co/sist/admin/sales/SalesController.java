package kr.co.sist.admin.sales;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SalesController {
	@Autowired
SalesService ss;
	
    @GetMapping("/manage/sales") // 브라우저에 /manage/dashboard을 입력하면 실행
    public String salesPage(@RequestParam(value = "safepayMonth", required = false) String safepayMonth,Model model) {
    	
    	if (safepayMonth == null || safepayMonth.isEmpty()) {
            safepayMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM"));
        }
        
        int monthNow = ss.selectSafepayMonthNow(safepayMonth);
        int monthLast = ss.selectSafepayLastMonth(safepayMonth);
        model.addAttribute("monthNow", monthNow);
        model.addAttribute("monthLast", monthLast);
        model.addAttribute("currentMonth", safepayMonth); // input에 다시 뿌려줄 값
    	
    	
    	
    	model.addAttribute("safepayToday",ss.selectSafepayToday());
    	model.addAttribute("safepayYesterday",ss.selectSafepayYesterday());
    	model.addAttribute("safepayMonth",ss.selectSafepayMonth());
    	
    	model.addAttribute("chargeNow",ss.selectCharge());
    	
    	model.addAttribute("categoryList",ss.selectCategoryList());
    	
    	
    	model.addAttribute("date",LocalDate.now());
    	model.addAttribute("now",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM월-dd일-HH시 mm분 ")));
    	
    	return "/manage/sales/sales"; 
    }

}
