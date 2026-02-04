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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.co.sist.user.buy.enums.PaymentStatus;

@RequestMapping("/order")
@Controller("UserOrderController")
public class OrderController {

	   private final Logger logger = LoggerFactory.getLogger(this.getClass());
	   
	   @Autowired
	   private BuyService bs;
	   
	   @ResponseBody
	   @PostMapping("/prepare")
	   public OrderDomain order(@RequestBody OrderRequestDTO orDTO, HttpSession session) {
		   //상품 정보 가져오기 및 주문 레코드 생성을 서비스에서 해줌.
		   String buyerId = (String)session.getAttribute("uid");
		   OrderDomain od = bs.orderProduct(orDTO.getPnum(),buyerId);
		   return od;
	   }

	    @GetMapping(value = "/toss")
	    public ResponseEntity<JSONObject> confirmPayment(@RequestBody PaymentDTO pDTO) throws Exception {

	        String orderId = pDTO.getOrderId();
	        int amountClient = pDTO.getAmount();
	        String paymentKey = pDTO.getPaymentKey();
	        String paymentType = pDTO.getPaymentType();
	        
	        
	        //DB랑 값이 일치하는지 비교
	        int amountDB = bs.searchOrderAmount(orderId);
	        if(amountDB < 0) {
	        	throw new IllegalStateException("존재하지 않는 주문");
	        }
	        if(amountDB != amountClient) {
	        	throw new IllegalStateException("결제 금액 위변조");
	        }
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
	        
	        if(isSuccess) {
	        	//결제한 내용을 결제 테이블에 저장함
	        	pDTO.setAmount(amountDB);
	        	pDTO.setPaymentStatus(PaymentStatus.APPROVED);
	        	
	        	bs.addPaymentInfo(pDTO);
	        	//주문상태 변경
	        	bs.modifyOrderStaus(orderId);
	        }else {
	        	//결제 실패했을 때 들어가는 내용
	        	//주문상태 FAIL로 변경해줄 것
	        }
	        
	        Map<String, Object> result = new HashMap<String, Object>();
	        result.put("success", isSuccess);
	        result.put("data", jsonObject);

	        return ResponseEntity.status(code).body(new JSONObject(result));
	    }
}