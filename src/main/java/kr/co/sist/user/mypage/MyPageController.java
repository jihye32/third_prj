package kr.co.sist.user.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/myPage")
@Controller
public class MyPageController {
	
	@Autowired
	private MyPageService mps;
	
	@GetMapping("/myPageFrm")
	public String myPageFrm(Model model, MyPageRangeDTO mprDTO, HttpSession session) {
		mprDTO.setPage(1);
//		mprDTO.setMyStoreNum(2);// 내 상점 번호
		int storeNum = 0; 
		if(session.getAttribute("snum") == null || "".equals(session.getAttribute("snum"))) {
			return "redirect:/user/login/loginFrm";
		}// end if
		storeNum = (Integer)session.getAttribute("snum");
		mprDTO.setMyStoreNum(storeNum);
		
		int pageScale = mps.pageScale();
		int page = 1;
		int totalCnt = mps.totalCnt(mprDTO);
		int startNum = mps.startNum(page);
		int endNum = mps.endNum(page, totalCnt);
		mprDTO.setTotalCnt(totalCnt);
		mprDTO.setEndNum(endNum);// 시작은 1, 더보기 클릭시 page++
		mprDTO.setStartNum(startNum);
		boolean hasNext = endNum < totalCnt;
		
		
		model.addAttribute("MyPageDomain", mps.searchMyPageInfo(storeNum));
		model.addAttribute("tempData", mps.searchMySellProdcut(mprDTO));
		model.addAttribute("cnt", totalCnt);// 물품 수
		model.addAttribute("hasNext", hasNext);
		model.addAttribute("storeNum", storeNum);
		
		return "mypage/myPageFrm";
	}// myPageFrm 
	
	@ResponseBody
	@GetMapping("/myPageFrm/more")
	public MyPageSearchDTO searchProduct( 
			MyPageRangeDTO mprDTO,
			HttpSession session
			){
		int storeNum = 0; 
		if(session.getAttribute("snum") == null || "".equals(session.getAttribute("snum"))) {
			return null;
		}// end if
		storeNum = (Integer)session.getAttribute("snum");
		mprDTO.setMyStoreNum(storeNum);

		int pageScale = mps.pageScale();
		int page = mprDTO.getPage();
		int totalCnt = mps.totalCnt(mprDTO);
		int startNum = mps.startNum(page);
		int endNum = mps.endNum(page, totalCnt);
		mprDTO.setTotalCnt(totalCnt);
		mprDTO.setEndNum(endNum);// 시작은 1, 더보기 클릭시 page++
		mprDTO.setStartNum(startNum);
		boolean hasNext = endNum < totalCnt;
		
		
		return new MyPageSearchDTO(mps.searchMySellProdcut(mprDTO), hasNext, totalCnt);
	}// searchProduct
	
	@GetMapping("/deleteCount")
	public String deleteCount(HttpSession session) {
		
		String id = ""; 
		int storeNum = 0; 
		id = (String)session.getAttribute("uid");
		storeNum = (Integer)session.getAttribute("snum");
		if(id != null && !"".equals(id) && storeNum != 0) {
			if(mps.removeCount(id, storeNum)) {
				return "redirect:/logout";
			}// end if 
		}// end if
		
//		mps.removeCount("user02", 2);
		
		return "redirect:/myPage/myPageFrm";
	}// deleteCount
	
	@PostMapping("/modifyProfile")
	public String modifyProfile(ProfileDTO pfDTO, HttpSession session) {
//		pfDTO.setUserId("user02");// ======== 가데이터
		
		if(session.getAttribute("uid") == null) {
			return "redirect:/user/login/loginFrm";
		} else {
			pfDTO.setUserId((String)session.getAttribute("uid"));
		}// end else
		
		mps.modifyProfile(pfDTO);
		
		return "redirect:/myPage/myPageFrm";
	}
	
	@GetMapping("/myPageFrm/purchased")
	public String purchased(Model model, HttpSession session) {
		
		String id  = "";
		if(session.getAttribute("uid") == null) {
			return "redirect:/user/login/loginFrm";
		} else {
			id = (String)session.getAttribute("uid");
		}// end else
		List<BuyProductDomain> list = mps.searchBuyProdcut(id);
		boolean flag = false;
		if(!list.isEmpty()) {
			flag = true;
		}
		model.addAttribute("list", list);
		model.addAttribute("flag", flag);
		
		return "mypage/wrapper_purchased :: fragmentPurchased";
	}// purchased
	
	@ResponseBody
	@GetMapping("/receive")
	public ReviewDTO receiveProduct(ReviewDTO rDTO, HttpSession session) {
		String id  = "";
		if(session.getAttribute("uid") == null) {
			return rDTO;
		} else {
			id = (String)session.getAttribute("uid");
		}// end else
		rDTO.setReviewerId(id);
		boolean flag = mps.modifyReceive(rDTO);
		rDTO.setReceiveFlag(flag);
		return rDTO;
	}// receiveProduct
	
	@ResponseBody
	@PostMapping("/review/insert")
	public ReviewDTO addReview(ReviewDTO rDTO, HttpSession session) {
		int myStroeNum  = 0;
		if(session.getAttribute("snum") == null) {
			return rDTO;
		} else {
			myStroeNum = (Integer)session.getAttribute("snum");
		}// end else
		rDTO.setMyStoreNum(myStroeNum);
		boolean flag = mps.addReview(rDTO);
		rDTO.setReviewAddFlag(flag);
		return rDTO;
	}// addReview
	
}// class
