package kr.co.sist.user.productdetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/product")
@Controller("UserProductDetailController")
public class ProductDetailController {

	@Autowired
	ProductDetailService ps;
	
	//상품 상세 화면 생성 및 상품 정보 가져오기
    @GetMapping("/{pnum}")
    public String searchProductDetail(@PathVariable int pnum, HttpServletRequest request , Model model) {
    	//서비스에서 상세정보 가져옴(DB연결하면 테스트)
//    	ProductDetailDomain pdd = ps.searchProduct(pnum);
//    	if(pdd == null) return "product_detail/product_detail";//메인화면으로 이동시키기
//    	SellerDomain sd = ps.searchSeller(pdd.getStore());
    	
    	ProductDetailDomain pdd = new ProductDetailDomain();
    	pdd.setBookmarkCnt(10);
    	pdd.setCategory("카테고리");
    	pdd.setChatCnt(0);
    	pdd.setContent("이것은 자바인가");
    	pdd.setDealAddr("양재동");
    	List<Integer> list = new ArrayList<Integer>();
    	list.add(1);
    	list.add(2);
    	pdd.setDealType(list);
    	List<String> slist = new ArrayList<String>();
    	slist.add("https://img2.joongna.com/media/original/2026/01/16/1768553238742cxo_wjZDC.jpg?impolicy=resizeWatermark3&amp;ftext=%EC%9D%B8%EC%B2%9C%EA%B3%BC%EC%95%88%EC%82%B0%EC%82%AC%EC%9D%B4");
    	slist.add("https://img2.joongna.com/media/original/2026/01/16/1768553238742BfR_PXEkb.jpg?impolicy=resizeWatermark3&amp;ftext=%EC%9D%B8%EC%B2%9C%EA%B3%BC%EC%95%88%EC%82%B0%EC%82%AC%EC%9D%B4");
    	pdd.setImg(slist);
    	pdd.setPrice(100000000);
    	pdd.setProduct_num(100);
    	pdd.setProductStatus("Y");
    	pdd.setSellStatusCode(1);//1: 판매중, 2: 예약중, 3: 판매완료
    	pdd.setThumbnail("https://img2.joongna.com/media/original/2026/01/16/176855323874331j_7cv0Z.jpg?impolicy=resizeWatermark3&amp;ftext=%EC%9D%B8%EC%B2%9C%EA%B3%BC%EC%95%88%EC%82%B0%EC%82%AC%EC%9D%B4");
    	pdd.setTitle("책책책책책책책책책");
    	pdd.setViewCnt(123);
    	
    	List<String> imgList = new ArrayList<String>();
    	imgList.add(pdd.getThumbnail());
    	for(String img : pdd.getImg()) {
    		imgList.add(img);
    	}
    	pdd.setImg(imgList);
    	
    	SellerDomain sd = new SellerDomain();
    	sd.setStoreName("자바");
    	sd.setStoreNum(456);
    	sd.setProductCnt(1);
    	sd.setReivewCnt(10);
    	sd.setSellerProfile("프로필 이미지명!!");
    	
    	
    	
    	HttpSession ss = request.getSession();
    	String store = (String)ss.getAttribute("storeName");
    	boolean isMe = false;
    	boolean sendFlag = false; //발송을 한 상태(발송버튼이 나오지 않음) 
//    	if(pdd.getStore().equals(store)) { //로그인한 사람이 작성한 글인지 확인
        if(isMe) { //로그인한 사람이 작성한 글인지 확인
        	isMe = true;
        	if(pdd.getSellStatusCode()==3) {//판매완료인 상태일 때 발송 확인
        		sendFlag = ps.searchSendFlag(pnum);
        		model.addAttribute("sendFlag", sendFlag);
        	}
        }else {
        	ps.modifyViewCnt(pnum);
        	pdd.setViewCnt(pdd.getViewCnt()+1);
        }
        
        model.addAttribute("storeCheck", isMe);
        model.addAttribute("ProductDetailDomain", pdd);
        model.addAttribute("SellerDomain", sd);
        
        return "product_detail/product_detail";
    }

	@PostMapping("/modifyUpDate")
	@ResponseBody // 리턴값을 JSON으로 변환해줍니다.
	public Map<String, Object> modifyUpDate(int pnum) {
		Map<String, Object> response = new HashMap<>();

		boolean flag = false;
    	int date = ps.searchUpDate(pnum);
    	if(date > 14) {
    		flag = true;
    		ps.modifyUpDate(pnum);
    	}
    	String resultMsg = flag ? "끌올됨" : "끌올안됨";
	    
	    response.put("msg", resultMsg);
	    response.put("flag", flag);
	   
	    return response; // 이제 HTML 파일명이 아닌 데이터가 전송됩니다.
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
	
	//판매 상태 변경
	@PostMapping("/status")
	@ResponseBody
    public ResponseEntity<String> updateStatus(SellStatusDTO ssDTO) {
        
        try {
            // 1. 서비스 레이어를 통해 DB 상태를 업데이트하는 로직이 들어갑니다.
            ps.modifyProductStatus(ssDTO);
            
            System.out.println("상품 ID: " + ssDTO.getPnum());
            System.out.println("변경할 상태: " + ssDTO.getSellStatusCode());

            // 2. 처리가 성공하면 성공 메시지(200 OK)를 보냅니다.
            return ResponseEntity.ok("상태 변경 성공");
            
        } catch (Exception e) {
            // 실패 시 에러 메시지(500 Internal Server Error 등)를 보냅니다.
            return ResponseEntity.status(500).body("서버 오류 발생");
        }
    }
//    // 3. 배송 완료 처리 (보라색 버튼)
//    @PostMapping("/{productId}/complete-shipping")
//    public String completeShipping(@PathVariable Long productId) {
//        productService.markAsShipped(productId);
//        return "redirect:/products/" + productId;
//    }
	
	//상품 삭제
	@PostMapping("/deleteProduct")
	@ResponseBody // 리턴값을 JSON으로 변환해줍니다.
	public Map<String, Object> removeProduct(int pnum) {
		Map<String, Object> response = new HashMap<>();
		boolean flag = ps.removeProduct(pnum);
		
    	String resultMsg = flag ? "삭제됨" : "삭제안됨";
	    
	    response.put("msg", resultMsg);
	    response.put("flag", flag);
		//삭제이 완료되면 마이페이지로, 아니면 화면 유지
		return response;
	}
	
	@GetMapping("/sellerPage")
	public String moveSellerPage(String sellerStore) {
		return "";//판매자 상세페이지 이동
	}
}