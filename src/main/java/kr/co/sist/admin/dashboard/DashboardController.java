package kr.co.sist.admin.dashboard; // 패키지 경로는 본인 구조에 맞게

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

   

    @GetMapping("/manage/dashboard") // 브라우저에 /manage/dashboard을 입력하면 실행
    public String dashboardPage(Model model) {
    	model.addAttribute("date",LocalDate.now());
    	model.addAttribute("now",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM월-dd일-HH시 mm분 ")));
    	
    	return "/manage/dashboard/dashboard"; 
    }
    
}