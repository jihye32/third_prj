package kr.co.sist.user.productlist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.ProductDomain;

@Controller
public class ProductListController {
	
	@Autowired(required = false) 
	private ProductListService pls;
	
	@GetMapping("/searchList")
	public String productList(Model model, ProductRangeDTO prDTO, HttpSession session) {
		
		// pagenation 관련
		if(session.getAttribute("snum") != null & !"".equals(session.getAttribute("snum"))) {
			prDTO.setUserId((Integer)session.getAttribute("snum"));
		}// end if
		int totalCnt = pls.totalCnt(prDTO);// 총 게시물의 수
		int pageScale = pls.pageScale();// 한화면에 보여줄 게시물의 수
		int totalPage = pls.totalPage(totalCnt, pageScale);// 총 페이지수
		int currentPage = prDTO.getCurrentPage();// 현재 페이지
		int startNum = pls.startNum(currentPage, pageScale);// 시작번호
		int endNum = pls.endNum(startNum, pageScale);// 끝번호
		prDTO.setUrl("/searchList");
		prDTO.setStartNum(startNum);
		prDTO.setEndNum(endNum);
		prDTO.setTotalPage(totalPage);
		model.addAttribute("totalCnt",totalCnt);
		
		List<ProductDomain> list = pls.searchProductList(prDTO);
		model.addAttribute("tempData", list);
		
		
		// 카테고리 작업
		String selCategoryName = "";
		int selCategoryNum =0;
		selCategoryNum = prDTO.getCategory();
		List<CategoryDomain> categorylist = pls.searchCategory();
		for(CategoryDomain cd : categorylist) {
			if(cd.getCategoryCode()==selCategoryNum) {
				selCategoryName = cd.getCategoryText();
			}// end if
		}// end for
		model.addAttribute("selCategoryName", selCategoryName);
		model.addAttribute("categorylist", categorylist);
		// 카테고리 작업 끝
		
		// 페이지네이션
		model.addAttribute("pagination", pls.pagination(prDTO));
		
		
		return "product_list/productList";
	}// productList
	
}// class
