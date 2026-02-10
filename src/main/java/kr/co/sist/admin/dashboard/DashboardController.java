package kr.co.sist.admin.dashboard; // 패키지 경로는 본인 구조에 맞게

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@Autowired
   DashboardService ds;

    @GetMapping("/manage/dashboard") // 브라우저에 /manage/dashboard을 입력하면 실행
    public String dashboardPage(Model model) {

    	        Map<String, Object> joinStats = ds.getJoinStats();
    	        Map<String, Object> visitStats = ds.getVisitStats();
    	        Map<String, Object> soldStats = ds.getSoldStats();
    	        Map<String, Object> ageStats = ds.getAgeStats();
    	        List<Map<String, Object>> uploadList = ds.getUploadStats();
    	        List<Map<String, Object>> soldItemList = ds.getSoldItemStats();
    	        List<Map<String, Object>> bestsellerList = ds.getBestsellers();

    	        model.addAttribute("joinToday", joinStats.get("JOINTODAY"));
    	        model.addAttribute("joinMonth", joinStats.get("JOINMONTH"));
    	        model.addAttribute("joinYear", joinStats.get("JOINYEAR"));

    	        model.addAttribute("visitToday", visitStats.get("VISITTODAY"));
    	        model.addAttribute("visitMonth", visitStats.get("VISITMONTH"));
    	        model.addAttribute("visitYear", visitStats.get("VISITYEAR"));

    	        model.addAttribute("soldToday", soldStats.get("SOLDTODAY"));
    	        model.addAttribute("soldMonth", soldStats.get("SOLDMONTH"));
    	        model.addAttribute("soldYear", soldStats.get("SOLDYEAR"));

    	        model.addAttribute("ageTen", ageStats.get("TEN"));
    	        model.addAttribute("ageTwenty", ageStats.get("TWENTY"));
    	        model.addAttribute("ageThirty", ageStats.get("THIRTY"));
    	        model.addAttribute("ageForty", ageStats.get("FORTY"));
    	        model.addAttribute("ageFifty", ageStats.get("FIFTY"));
    	        model.addAttribute("ageSixty", ageStats.get("SIXTY"));

    	        model.addAttribute("uploadList", uploadList);
    	        model.addAttribute("soldList", soldItemList);
    	        model.addAttribute("bestsellerList", bestsellerList);

    	
    	model.addAttribute("date",LocalDate.now());
    	model.addAttribute("now",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM월-dd일-HH시 mm분 ")));
    	
    	return "/manage/dashboard/dashboard"; 
    }
    
}
