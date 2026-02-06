package kr.co.sist.user.buy;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller("UserOrderController")
public class OrderController {

	   private final Logger logger = LoggerFactory.getLogger(this.getClass());

	    @RequestMapping(value = "/buy/toss",method = {GET,POST})
	    public String confirmPayment(@ModelAttribute PaymentDTO pDTO) throws Exception {

	        JSONParser parser = new JSONParser();
	        String orderId = pDTO.getOrderId();
	        int amount = pDTO.getAmount();
	        String paymentKey = pDTO.getPaymentKey();
	        JSONObject obj = new JSONObject();
	        
	        obj.put("orderId", orderId);
	        obj.put("amount", amount);
	        obj.put("paymentKey", paymentKey);

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
	        JSONObject jsonObject = (JSONObject) parser.parse(reader);
	        responseStream.close();
	        
	        if(!isSuccess) {
	        	return "redirect:/buy/sell_fail :: sellFailFrm";
	        }

	        return "redirect:/buy/sell_success :: sellSuccessFrm";
	    }
}