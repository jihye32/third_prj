package kr.co.sist.user.productlist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.sist.user.ProductDomain;

@Controller
public class ProductListController {
	
	@Autowired(required = false) 
	private ProductListService pls;
	
	@GetMapping("/searchList")
	public String productList(Model model, ProductRangeDTO prDTO) {
//		System.out.println(prDTO);
		ProductDomain pd = null;
		List<ProductDomain> list = new ArrayList<ProductDomain>();
		for(int i =1; i<=10;i++) {
//			pd = new ProductDomain("https://img2.joongna.com/media/original/2026/01/18/1768736344461BM3_LaDt3.jpg?impolicy=thumb&amp;size=150", "판매 상태 표시", "상품 제목", "거래 지역", null, null, 1, 1, 10000, i, null);
			pd.setProductName(pd.getProductName()+i);
			list.add(pd);
		} 
		model.addAttribute("tempData", list);
		 
		
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
		
		return "/product_list/productList";
	}// productList
	
}// class
