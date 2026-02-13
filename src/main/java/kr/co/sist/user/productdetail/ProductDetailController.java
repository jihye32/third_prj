package kr.co.sist.user.productdetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.productdetail.enums.DealType;
import kr.co.sist.user.productdetail.enums.ProductStatus;
import kr.co.sist.user.productdetail.enums.SellStatus;

@RequestMapping("/product")
@Controller("UserProductDetailController")
public class ProductDetailController {

	@Autowired
	ProductDetailService ps;

	//상품 상세 화면 생성 및 상품 정보 가져오기
    @GetMapping("/{pnum}")
    public String searchProductDetail(@PathVariable int pnum, HttpSession ss , Model model) {
    	//서비스에서 상세정보 가져옴
    	ProductDetailDomain pdd = ps.searchProduct(pnum);
    	if(pdd == null) return "redirect:/";//메인화면으로 이동시키기
    	if("Y".equals(pdd.getDeleteFlag())) return "redirect:/";
    	
    	List<String> imgList = new ArrayList<String>();
    	imgList.add(pdd.getThumbnail());
    	if(pdd.getImages()!=null) {
    		for(String img : pdd.getImages()) {
    			imgList.add(img);
    		}
    	}
    	pdd.setImages(imgList);
    	
    	SellerInfoDomain sid = ps.searchSeller(pdd.getSellerId());//판매자 상점 번호로 판매자 정보 가져오기
    	
    	String uid = (ss.getAttribute("uid") != null) ? (String)ss.getAttribute("uid") : null;//현재 로그인한 유저의 store number 가져오기
    	boolean isMe = false; //본인확인
    	System.out.println(uid);
    	System.out.println(sid.getId());
    	//세션에서 로그인을 햇는지 확인할 것.
    	if(sid.getId().equals(uid)) {
    		isMe = true;
    		if(pdd.getSellStatusCode()==3) {//판매완료인 상태일 때 발송 확인
    			boolean sendFlag = ps.searchSendFlag(pnum); //발송 완료/null false, 발송 안함 true
    			model.addAttribute("sendFlag", sendFlag);
    		}
    	}else {
    		ps.modifyViewCnt(pnum);
    		if(uid != null) {//로그인 한 상태이므로 북마크 확인
    			BookmarkDTO bDTO = new BookmarkDTO();
    			bDTO.setPnum(pnum);
    			bDTO.setSnum((int)ss.getAttribute("snum"));
    			System.out.println(ss.getAttribute("snum"));
    			boolean bookmark = (ps.searchBookmark(bDTO)!=null?true:false);//해당 상품(pnum)에 대해 로그인한 사람(snum)이 북마크를 해놨는지 확인
    			model.addAttribute("bookmarkFlag", bookmark);
    		}
    	}
    	System.out.println(isMe);
        model.addAttribute("storeCheck", isMe);
        model.addAttribute("Product", pdd);
        model.addAttribute("SellerInfo", sid);
        model.addAttribute("pnum", pnum);
        
        //enum 사용 시 필요한 부분
        model.addAttribute("ProductStatus", ProductStatus.class);
        model.addAttribute("SellStatus", SellStatus.class);
        model.addAttribute("DealType", DealType.class);
        
        return "product_detail/product_detail";
    }
    

	@PostMapping("/bookmark")
	@ResponseBody
    public void addBookmark(@RequestBody BookmarkDTO bDTO, HttpSession session) {
		int snum = (int)session.getAttribute("snum");
		bDTO.setSnum(snum);
		ps.addBookmark(bDTO);
	}
	@DeleteMapping("/bookmark")
	@ResponseBody
	public void removeBookmark(@RequestBody BookmarkDTO bDTO, HttpSession session) {
		int snum = (int)session.getAttribute("snum");
		bDTO.setSnum(snum);
		ps.removeBookmark(bDTO);
	}

	
	@PostMapping("/modifyUpDate")
	@ResponseBody // 리턴값을 JSON으로 변환해줍니다.
	public Map<String, Object> modifyUpDate(@RequestBody int pnum) {
		Map<String, Object> response = new HashMap<>();

		boolean flag = false;
    	boolean check = ps.searchUpDate(pnum);
    	if(check) {
    		flag = true;
    		ps.modifyUpDate(pnum);
    	}
    	String resultMsg = flag ? "끌올 되었습니다." : "끌올 되지 못했습니다. 잠시 후 다시 시도해주세요.";
	    
	    response.put("msg", resultMsg);
	    response.put("flag", flag);
	   
	    return response; // 이제 HTML 파일명이 아닌 데이터가 전송됩니다.
	}
    
	
	
    
	//판매 상태 변경
	@PostMapping("/status")
	@ResponseBody
    public Map<String, Object> updateStatus(@RequestBody SellStatusDTO ssDTO) {
		Map<String, Object> response = new HashMap<>();
		System.out.println(ssDTO.getPnum());
		System.out.println(ssDTO.getSellStatusCode());
		boolean flag = false;
		flag = ps.modifyProductStatus(ssDTO);
		if(flag && ssDTO.getSellStatusCode()==3) {
			ps.modifyProductOver(ssDTO.getPnum());
		}
		String resultMsg = flag ? "상태 변경됨" : "상태 변경 안됨";
		response.put("msg", resultMsg);
	    response.put("flag", flag);
	   
	    return response; // 이제 HTML 파일명이 아닌 데이터가 전송됩니다.
    }

	//상품 삭제
	@PostMapping("/deleteProduct")
	@ResponseBody // 리턴값을 JSON으로 변환해줍니다.
	public Map<String, Object> removeProduct(@RequestBody int pnum, HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		boolean flag = ps.modifyDeleteFlag(pnum);
		
    	String resultMsg = flag ? "삭제됨" : "삭제안됨";
	    
	    response.put("msg", resultMsg);
	    response.put("flag", flag);
		//삭제이 완료되면 마이페이지로, 아니면 화면 유지
	    response.put("snum", session.getAttribute("snum"));
		return response;
	}
	
	 // 3. 배송 완료 처리 (보라색 버튼)
    @PostMapping("/send")
    @ResponseBody
    public Map<String, Object> completeShipping(@RequestBody int pnum) {
    	Map<String, Object> response = new HashMap<>();
		boolean flag = ps.modifyProductSend(pnum);
		
    	String resultMsg = flag ? "완료됨" : "완료안됨";
	    
	    response.put("msg", resultMsg);
	    response.put("flag", flag);
		return response;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
	//게시글 수정으로 이동
	@GetMapping("/modify/{pnum}")
	public String modifyProduct(@PathVariable int pnum, Model model) {
		ProductDetailDomain pdd = ps.searchProduct(pnum);
		if(pdd == null) return "javascript:history.back()";
		model.addAttribute("ProductDetailDomain", pdd);
		return "product_detail/product_modify";
	}
	@PostMapping("/modifyProduct")
	@ResponseBody // 리턴값을 JSON으로 변환해줍니다.
	public Map<String, Object> modifyProductProccess(ProductModifyDTO pmDTO) {
		Map<String, Object> response = new HashMap<>();
		boolean flag = ps.modifyProductDetail(pmDTO);
		
    	String resultMsg = flag ? "수정됨" : "수정안됨";
	    
	    response.put("msg", resultMsg);
	    response.put("flag", flag);
		//수정이 완료되면 원래의 상세화면으로, 아니면 화면 유지
		return response;
	}
	
	
   
	

	
	@GetMapping("/sellerPage")
	public String moveSellerPage(String sellerStore) {
		return "";//판매자 상세페이지 이동
	}
}