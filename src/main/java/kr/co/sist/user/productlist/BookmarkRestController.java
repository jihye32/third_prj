package kr.co.sist.user.productlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class BookmarkRestController {
	
	@Autowired
	private ProductListService pls;

	@PostMapping("/bookmark/{productNum}")
	public BookmarkDTO addBookmark(@PathVariable int productNum, HttpSession session) {
		BookmarkDTO bDTO = new BookmarkDTO();
		int storeNum = 0;
		if(session.getAttribute("snum") != null) {
			storeNum = (Integer)session.getAttribute("snum");
		}else {
			bDTO.setResultFlag(false);
			return bDTO;
		}// else
		bDTO.setProductNum(productNum);
		bDTO.setStoreNum(5);
		boolean flag = pls.addBookmark(bDTO);
		bDTO.setResultFlag(flag);
		
		return bDTO;
	}// addBookmark
	
	@DeleteMapping("/bookmark/{productNum}")
	public BookmarkDTO removeBookmark(@PathVariable int productNum, HttpSession session) {
		BookmarkDTO bDTO = new BookmarkDTO();
		bDTO.setProductNum(productNum);
		int storeNum = 0;
		if(session.getAttribute("snum") != null) {
			storeNum = (Integer)session.getAttribute("snum");
		}// end if
		bDTO.setStoreNum(storeNum);
		boolean flag = pls.removeBookmark(bDTO);
		bDTO.setResultFlag(flag);
		
		return bDTO;
	}// addBookmark
	
	
}// class
