package kr.co.sist.user;
import kr.co.sist.admin.login.LoginController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;


; 

@Controller
public class MainController {

    private final LoginController loginController;

    MainController(LoginController loginController) {
        this.loginController = loginController;
    }
	@RequestMapping(value = "/", method = {GET, POST})
	public String testIndex(Model model, HttpSession session) {
		ProductDomain pd = null;
		int modelCnt = 1;
		List<ProductDomain> list = new ArrayList<ProductDomain>();
		int eleCnt = 1;
		for(int j = 0 ; j < 30 ; j+=6) {
			for(int k = 1 ; k <= 6 ; k++) {
				pd = new ProductDomain("https://img2.joongna.com/media/original/2026/01/18/1768736344461BM3_LaDt3.jpg?impolicy=thumb&amp;size=150", "판매 상태 표시", "많이 본 상품 제목"+eleCnt, "거래 지역", eleCnt, 1, 10000, null);
				list.add(pd);
				System.out.println(list);
				eleCnt++;
			}// end for
			model.addAttribute("tempdata"+modelCnt,list);
			System.out.println(list);
			list= new ArrayList<ProductDomain>();
			modelCnt++;
		}// end for
		
		int i = 1;
		List<ProductDomain> list2= new ArrayList<ProductDomain>();
		int cnt = 31;
		for(int j = 0 ; j < 30 ; j+=6) {
			for(int k = 1 ; k <= 6 ; k++) {
				pd = new ProductDomain("https://img2.joongna.com/media/original/2026/01/18/1768736344461BM3_LaDt3.jpg?impolicy=thumb&amp;size=150", "판매 상태 표시", "찜 많은 상품 제목"+cnt, "거래 지역", cnt, 1, 10000, null);
				list2.add(pd);
				cnt++;
			}// end for
			model.addAttribute("temp2data"+i,list2);
			list2= new ArrayList<ProductDomain>();
			i++;
		}// end for
		
		session.setAttribute("uid", "!234");
		
		return "index";
	}// testIndex
}// class
