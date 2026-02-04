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
	
	
	
	
	public int insertPaymentInfo(PaymentDTO pDTO) {
		int cnt = 0;
		
		return cnt;
	}
}
