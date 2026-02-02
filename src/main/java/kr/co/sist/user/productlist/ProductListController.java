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
		System.out.println(prDTO);
		ProductDomain pd = null;
		List<ProductDomain> list = pls.searchProductList();
		model.addAttribute("tempData", list);
		
		pls.searchProductTotalCnt(prDTO);
		
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
