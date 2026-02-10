package kr.co.sist.admin.sales;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SalesController {
	@Autowired
SalesService ss;
	
    @GetMapping("/manage/sales") // 브라우저에 /manage/sales을 입력하면 실행
    public String salesPage(@RequestParam(value = "currentMonth", required = false) String currentMonth,Model model) {
    	
    	if (currentMonth == null || currentMonth.isEmpty()) {
    		currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM"));
        }
        
        int safepayMonthNow = ss.selectSafepayMonthNow(currentMonth);
        int safepayMonthLast = ss.selectSafepayLastMonth(currentMonth);
        model.addAttribute("safepayMonthNow", safepayMonthNow);
        model.addAttribute("safepayMonthLast", safepayMonthLast);
        model.addAttribute("currentMonth", currentMonth); // input에 다시 뿌려줄 값
        int chargeMonthNow = ss.selectChargeMonthNow(currentMonth);
        int chargeMonthLast = ss.selectChargeLastMonth(currentMonth);
        model.addAttribute("chargeMonthNow", chargeMonthNow);
        model.addAttribute("chargeMonthLast", chargeMonthLast);
    	
    	
    	model.addAttribute("safepayToday",ss.selectSafepayToday());
    	model.addAttribute("safepayYesterday",ss.selectSafepayYesterday());
    	model.addAttribute("safepayMonth",ss.selectSafepayMonth());
    	model.addAttribute("chargeToday",ss.selectChargeToday());
    	model.addAttribute("chargeYesterday",ss.selectChargeYesterday());
    	model.addAttribute("chargeMonth",ss.selectChargeMonth());
    	
    	model.addAttribute("chargeNow",ss.selectCharge());
    	
    	model.addAttribute("categoryList",ss.selectCategoryList());
    	
    	
    	
    	
    	model.addAttribute("allPayMethod",ss.allPayMethod());
    	model.addAttribute("selectPayMethod",ss.selectPayMethod());

    	model.addAttribute("lastYearChargeList",ss.selectLastYearCharges());
    	
    	
    	model.addAttribute("date",LocalDate.now());
    	model.addAttribute("now",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM월-dd일-HH시 mm분 ")));
    	
    	return "/manage/sales/sales"; 
    }
    
    @PostMapping("/insert-charge")
    public String insertCharge(@RequestParam("chargeNow") Double chargeNow) {
        System.out.println("변경할 수수료율: " + chargeNow);
        
        ss.insertCharge(chargeNow);

        return "redirect:/manage/sales";
    }

}
