package kr.co.sist.user.buy;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.MyBatisHandler;

@Component("UserBuyDAO")
public class BuyDAO {
	//구매 상품 정보 얻는 함수	
	public BuyDomain selectProduct(int pnum) {
		BuyDomain bd= null;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		bd=ss.selectOne("kr.co.sist.user.buy.selectProduct", pnum);
		
		if( ss != null) { ss.close(); }//end if
		return bd;
	}
	
	public BigDecimal selectCharge() {
		BigDecimal charge= null;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		charge=ss.selectOne("kr.co.sist.user.buy.selectCharge");
		
		if( ss != null) { ss.close(); }//end if
		
		return charge;
	}
	
	public List<Integer> selectProductDealType(int pnum) {
		List<Integer> codeList = null;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		codeList=ss.selectList("kr.co.sist.user.buy.selectProductSellCode", pnum);
		
		if( ss != null) { ss.close(); }//end if
		return codeList;
	}
	
	
	//구매할 때 필요한 함수
	//주문 정보 저장
	public boolean insertOrderInfo(OrderDTO oDTO) {
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		int cnt=ss.insert("kr.co.sist.user.buy.insertOrder", oDTO);
		
		if( ss != null) { ss.close(); }//end if
		return cnt==1;
	}
	
	//저장된 금액 가져오기
	public Integer selectOrderAmount(String orderId) {
		Integer amount = null;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		amount = ss.selectOne("kr.co.sist.user.buy.selectAmount", orderId);
		
		if( ss != null) { ss.close(); }//end if
		
		return amount;
	}
	
	//결제 완료 시 결제 정보 저장
	public int insertPaymentInfo(PaymentDTO pDTO) {
		int cnt = 0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.insert("kr.co.sist.user.buy.insertPayment", pDTO);
		
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}
	
	//주문의 상태 변경
	//현재 성공했을 때만 적용 추후 수정되어야함
	public int updateOrderStaus(String orderId) {
		int cnt = 0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.update("kr.co.sist.user.buy.updateOrderStatus", orderId);
		
		if( ss != null) { ss.close(); }//end if
		return cnt;
	}
	
	//상품 번호 조회
	public Integer selectProductNum(String orderId) {
		Integer pnum = null;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		pnum = ss.selectOne("kr.co.sist.user.buy.selectProductNum", orderId);
		
		if( ss != null) { ss.close(); }//end if
		
		return pnum;
	}
	
	//상품 상태 변경
	public int updateProductStatus(int pnum) {
		int cnt = 0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.update("kr.co.sist.user.buy.updateProductStatus", pnum);
		
		if( ss != null) { ss.close(); }//end if
		return cnt;
	}
	
	public PaymentDomain selectPayment(String orderId) {
		PaymentDomain pd = null;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		pd = ss.selectOne("kr.co.sist.user.buy.selectPaymnet", orderId);
		
		if( ss != null) { ss.close(); }//end if
		
		return pd;
	}
	
}
