package kr.co.sist.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.chat.ChatDTO;
import kr.co.sist.user.chat.ChatService;

@Controller
public class AdminProductController {

	@Autowired
	private AdminProductService ps;
	@Autowired
	private ChatService chatService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@ModelAttribute
	public void checkAdminLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
	    if (session == null || session.getAttribute("loginAdmin") == null) {
	        
	        String loginUrl = request.getContextPath() + "/manage";
	        
	        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        } else {
	            response.sendRedirect(loginUrl);
	        }
	    }
	}
	
	
	@GetMapping("/manage/product/product_main")
	public String memberMainPage(AdminProductDTO pDTO,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "sort", required = false) String sort, Model model) {

		pDTO.setCurrentPage(currentPage);
		if (sort != null)
			pDTO.setSortBy(sort);
		pDTO.setNumbers();

		int totalCount = ps.getProductTotalCount(pDTO);
		List<AdminProductDomain> productList = ps.getProductList(pDTO);
		String pagination = ps.getPaginationHtml(pDTO, totalCount);

		model.addAttribute("productList", productList);
		model.addAttribute("pagination", pagination);

		return "manage/product/product_main";
	}

	/**
	 * 상품 상세 관리 페이지 (정보 및 이미지 분리 조회)
	 */
	@GetMapping("/manage/product/product_detail")
	public String memberDetailPage(@RequestParam(value = "product_num", defaultValue = "0") int ProductNo, Model model) {
	    AdminProductDomain product = ps.getProductDetail(ProductNo);
	    List<String> productImages = ps.getProductImages(ProductNo);

	    model.addAttribute("product", product); 
	    model.addAttribute("productImages", productImages);

	    return "manage/product/product_detail";
	}
	
	@PostMapping("/manage/product/delete_product")
	@ResponseBody 
	public String deleteProduct(AdminProductDTO pDTO) {
	    int result = ps.removeProduct(pDTO); 
	    
	    AdminProductDomain product = ps.getProductDetail(pDTO.getProduct_num());
    	ChatDTO cDTO = new ChatDTO();
    	cDTO.setProductNum(product.getProduct_num());
    	cDTO.setWriterId("SYSTEM");
    	cDTO.setType("TEXT");
    	//해당 상품의 판매자 아이디
    	cDTO.setOtherId(product.getUser_id());
    	
    	StringBuilder msg = new StringBuilder();
    	msg.append("상품 ").append(product.getTitle()).append("이 ").append(product.getDelete_text()).append("에 해당하여 삭제되었습니다.");
    	cDTO.setContent(msg.toString());
    	
    	Integer roomNum = chatService.searchChatRoom(cDTO.getWriterId(), cDTO.getOtherId());
    	cDTO.setRoomNum(roomNum);

    	//실시간 메시지 전달
    	chatService.sendMessage(cDTO);
    	
        messagingTemplate.convertAndSend("/topic/room/" + roomNum, cDTO);
	    return (result > 0) ? "success" : "fail";
	}
}