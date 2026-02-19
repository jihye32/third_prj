package kr.co.sist.admin.sales;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SalesController {
	

    @GetMapping("/manage/sales") // 브라우저에 /manage/dashboard을 입력하면 실행
    public String dashboardPage(Model model) {
    	model.addAttribute("date",LocalDate.now());
    	model.addAttribute("now",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM월-dd일-HH시 mm분 ")));
    	
    	return "/manage/sales/sales"; 
    }

}
