package kr.co.sist.admin.faq;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @GetMapping("/manage/faq/faq")
    public String faqListPage(Model model) {
        
        model.addAttribute("menu", "customer"); 
        model.addAttribute("subMenu", "faq");
        
       
        return "manage/faq/faq"; 
    }
}