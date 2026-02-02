package kr.co.sist.xxx;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.sist.user.productdetail.ProductDetailDAO;
import kr.co.sist.user.productdetail.ProductDetailService;

@SpringBootTest
class ThirdPrjApplicationTests {

	@Test
	void contextLoads() {
//		ProductDetailService pds = new ProductDetailService();
//		pds.modifyViewCnt(1);
		ProductDetailDAO pdDAO = new ProductDetailDAO();
		int cnt = pdDAO.updateViewCnt(1);
		assertEquals(1,cnt);
	}

}
