package kr.co.sist.user.buy;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.buy.enums.PaymentStatus;
import kr.co.sist.user.chat.ChatDTO;
import kr.co.sist.user.chat.ChatService;
import kr.co.sist.user.productdetail.enums.DealType;

@RequestMapping("/order")
@Controller("UserOrderController")
public class OrderController {

	   @Autowired
	   private BuyService bs;
	   @Autowired
	   private ChatService chatService;
	   @Autowired
	   private SimpMessagingTemplate messagingTemplate;
	   
	   @ResponseBody
	   @PostMapping("/prepare")
	   public OrderDomain order(@RequestBody OrderRequestDTO orDTO, HttpSession session) {
		   //상품 정보 가져오기 및 주문 레코드 생성을 서비스에서 해줌.
		   String buyerId = (String)session.getAttribute("uid");
		   DealType dealType = orDTO.getDealType();
		   AddressDTO address = orDTO.getAddress();

		   if (dealType == DealType.DELIVERY) {
			   if(address == null) {
				   throw new IllegalArgumentException("배송지 정보가 필요합니다.");
			   }else {
				   String name = address.getName();
				   String tel = String.valueOf(address.getTel());
				   String addr = address.getAddr();
				   
				   if(name == null || "".equals(name)) {
					   throw new IllegalArgumentException("이름이 입력되지 않았습니다.");
				   }
				   if(name.length() > 20) {
					   throw new IllegalArgumentException("이름은 20자 이하만 가능합니다.");
				   }
				   if(tel == null || !tel.matches("^010\\d{8}$")) {
					    throw new IllegalArgumentException("휴대폰 번호 형식이 올바르지 않습니다.");
				   }
				   if(addr == null || "".equals(addr)) {
					   throw new IllegalArgumentException("주소가 입력되지 않았습니다.");
				   }
			   }
		   }

		   OrderDomain od = bs.orderProduct(orDTO,buyerId);
		   
		   return od;
	   }

	    @GetMapping(value = "/toss")
	    public String confirmPayment(@RequestParam String orderId, @RequestParam String paymentKey,
	    		@RequestParam int amount,@RequestParam String paymentType, Model model, HttpSession session) throws Exception {

	        
	        //DB랑 값이 일치하는지 비교
	        int amountDB = bs.searchOrderAmount(orderId);
	        if(amountDB < 0) {
	        	throw new IllegalStateException("존재하지 않는 주문");
	        }
	        if(amountDB != amount) {
	        	throw new IllegalStateException("결제 금액 위변조");
	        }
	        
	        JSONObject obj = new JSONObject();
	        obj.put("orderId", orderId);
	        obj.put("amount", amountDB);     // ✅ DB 금액 사용 (클라 금액 말고)
	        obj.put("paymentKey", paymentKey);
	        
	        //key는 properties로 분리해주기
	        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
	        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
	        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
	        Base64.Encoder encoder = Base64.getEncoder();
	        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
	        String authorizations = "Basic " + new String(encodedBytes);

	        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
	        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestProperty("Authorization", authorizations);
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setRequestMethod("POST");
	        connection.setDoOutput(true);

	        OutputStream outputStream = connection.getOutputStream();
	        outputStream.write(obj.toString().getBytes("UTF-8"));
	        int code = connection.getResponseCode();
	        boolean isSuccess = code == 200;

	        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

	        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
	        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
	        JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
	        responseStream.close();
	        
	        model.addAttribute("orderId", orderId);
	        if(!isSuccess) {
	        	//결제 실패했을 때 들어가는 내용
	        	//주문상태 FAIL로 변경해줄 것
	        	String failMsg = null;

	        	JSONObject failure = (JSONObject) jsonObject.get("failure");
	        	if (failure != null) {
	        	    failMsg = (String) failure.get("message");
	        	}
	        	if (failMsg == null) {
	        	    // 혹시 에러 응답 포맷이 다른 경우 대비
	        	    Object msg = jsonObject.get("message");
	        	    if (msg != null) failMsg = msg.toString();
	        	}
	        	model.addAttribute("url", "/buy/fail/"+orderId+"?msg="+failMsg);

	        	return "/buy/bridge";
	        }
	        
	        PaymentDTO pDTO = new PaymentDTO();
        	//결제한 내용을 결제 테이블에 저장함
        	pDTO.setOrderId(orderId);
        	pDTO.setAmount(amountDB);
        	pDTO.setPaymentKey(paymentKey);
        	pDTO.setMethod((String)jsonObject.get("method"));
        	
        	String provider = "TOSS";
        	JSONObject easyPay = (JSONObject) jsonObject.get("easyPay");
        	if (easyPay != null) {
        	    String providerKo = (String) easyPay.get("provider"); // "카카오페이"
        	    if (providerKo != null) {
        	        switch (providerKo) {
        	            case "카카오페이": provider = "KAKAOPAY"; break;
        	            case "네이버페이": provider = "NAVERPAY"; break;
        	            case "토스페이": provider = "TOSSPAY"; break;
        	            default: provider = "ETC"; break;
        	        }
        	    }
        	}
        	pDTO.setProvider(provider);

        	pDTO.setPaymentStatus(PaymentStatus.APPROVED);
        	
        	String approvedAt = (String) jsonObject.get("approvedAt");
        	Timestamp approvedDate = Timestamp.from(OffsetDateTime.parse(approvedAt).toInstant());
        	pDTO.setApprovedDate(approvedDate);
        	
        	bs.addPaymentInfo(pDTO);
        	//주문상태 변경
        	bs.modifyOrderStaus(orderId);
        	//상품상태 변경
        	int pnum = bs.searchProductNum(orderId);
        	bs.modifyProductStaus(pnum);
        	
        	//성공하면 주문에 대한 내용을 DB에 저장
        	String buyerId = (String)session.getAttribute("uid");
        	ChatDTO cDTO = new ChatDTO();
        	cDTO.setProductNum(pnum);
        	cDTO.setWriterId(buyerId);  // 또는 "SYSTEM"
        	String sellerId = bs.searchSellerId(pnum);
        	cDTO.setOtherId(sellerId); //판매자 아이디(찾아야함)
        	AddressDTO address = bs.searchAddress(orderId); //주소 찾기
        	
        	StringBuilder msg = new StringBuilder();
        	msg.append("구매가 완료되었습니다.");
        	if(address != null) {
        		msg.append("\n배송 정보\n").append("수령자 : ");
        		msg.append(address.getName());
        		msg.append("\n전화번호 : ");
        		msg.append(address.getTel());
        		msg.append("\n주소 : ");
        		msg.append(address.getAddr());
        		msg.append("\n상세주소 : ");
        		if(address.getAddrDetail() == null) {
        			address.setAddrDetail(""); 
        		}
        		msg.append(address.getAddrDetail());
        	}
        	cDTO.setContent(msg.toString());
        	
        	Integer roomNum = chatService.searchChatRoom(sellerId, buyerId);
        	cDTO.setRoomNum(roomNum);

        	//실시간 메시지 전달
        	chatService.sendMessage(cDTO);
        	
            messagingTemplate.convertAndSend("/topic/room/" + roomNum, cDTO);
	        
        	model.addAttribute("url", "/buy/success/"+orderId);

        	return "/buy/bridge";
	    }
	    
	    
	    
	    @ExceptionHandler(IllegalArgumentException.class)
	    @ResponseBody
	    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
	        return ResponseEntity
	                .badRequest()
	                .body(e.getMessage());
	    }
}