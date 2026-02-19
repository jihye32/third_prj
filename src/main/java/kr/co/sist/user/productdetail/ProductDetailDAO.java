package kr.co.sist.user.productdetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository("UserProductDetailDAO")
public class ProductDetailDAO {
//////product detail domain에 들어갈 내용/////////////////////////////////////////////////////////////////////
	//선택된 상품 번호로 상품의 정보 조회
	public ProductDetailDomain selectProduct(int pnum) {
		ProductDetailDomain pdd = null;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		pdd=ss.selectOne("kr.co.sist.user.product.detail.selectProduct", pnum);
		
		if( ss != null) { ss.close(); }//end if
		
		return pdd;
	}//selectProduct
	
	//직거래 주소
	public String selectAddress(int pnum) {
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		String addr=ss.selectOne("kr.co.sist.user.product.detail.selectAddress", pnum);
		
		if( ss != null) { ss.close(); }//end if
		
		return addr;
	}//selectProduct

	//선택된 상품 번호에 등록한 태그들 가져옴
	public List<String> selectTag(int pnum){
		List<String> list = new ArrayList<String>();
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		list=ss.selectList("kr.co.sist.user.product.detail.selectProductTag", pnum);
		if( ss != null) { ss.close(); }//end if
		
		return list;
	}//selectTag
	
	//선택된 상품 번호에 등록한 이미지들 가져옴
	public List<String> selectImg(int pnum){
		List<String> list = new ArrayList<String>();
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		list=ss.selectList("kr.co.sist.user.product.detail.selectProductImg", pnum);
		if( ss != null) { ss.close(); }//end if
		return list;
	}//selectImg
	
	//선택된 상품 번호에 등록한 거래방법들 가져옴
	public List<Integer> selectDealType(int pnum){
		List<Integer> list = new ArrayList<Integer>();
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		list=ss.selectList("kr.co.sist.user.product.detail.selectProductSellCode", pnum);
		
		if( ss != null) { ss.close(); }//end if
		return list;
	}//selectDealType
	
	//상점명으로 판매 등록한 모든 상품의 개수 가져옴
	public int cntBookmark(int pnum) {
		int cnt =0;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		cnt=ss.selectOne("kr.co.sist.user.product.detail.selectProductBookmark", pnum);
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//cntBookmark
	
	//상점명으로 판매 등록한 모든 상품의 개수 가져옴
	public int cntChat(int pnum) {
		int cnt =0;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		cnt=ss.selectOne("kr.co.sist.user.product.detail.selectProductChat", pnum);
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//cntChat
	
	//발송완료 확인
	public OrderDomain selectSendFlag(int pnum) {
		OrderDomain od = null;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		od=ss.selectOne("kr.co.sist.user.product.detail.selectOrderInfo", pnum);
		if( ss != null) { ss.close(); }//end if
		
		return od;
	}//selectSendFlag

	
//////seller domain에 들어갈 내용/////////////////////////////////////////////////////////////////////
	public SellerInfoDomain selectSellerInfo(int ssId) {
		SellerInfoDomain sd = null;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		sd=ss.selectOne("kr.co.sist.user.product.detail.sellerInfo", ssId);
		if( ss != null) { ss.close(); }//end if
		
		return sd;
	}//selectSellerInfo
	//상점명으로 판매 등록한 모든 상품의 개수 가져옴
	public int cntSellProduct(int ssId) {
		int cnt =0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		cnt=ss.selectOne("kr.co.sist.user.product.detail.sellerProductCnt", ssId);
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//cntSellProduct
	
	//상점명으로 등록되어있는 리뷰의 개수 가져옴
	public int cntReview(int ssId) {
		int cnt =0;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		cnt=ss.selectOne("kr.co.sist.user.product.detail.sellerReviewCnt", ssId);
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//cntReview
	
//////게시글 수정사항/////////////////////////////////////////////////////////////////////
	//정상적인 확인을 위해서 boolean를 반환하지만 완성되고는 반환할 필요x
	//같은 아이디가 아닐 경우 조회수 증가
	public int updateViewCnt(int pnum) {
		int cnt =0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.update("kr.co.sist.user.product.detail.updateViewCnt", pnum);
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//updateViewCnt
	
	//찜하기 했는지 확인
	public String selectBookmark(BookmarkDTO bDTO) {
		String bookmark = "";
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		bookmark=ss.selectOne("kr.co.sist.user.product.detail.selectBookmark", bDTO);
		if( ss != null) { ss.close(); }//end if
		
		return bookmark;
	}//selectBookmark
	
	//찜하기 누르면 추가
	public int insertBookmark(BookmarkDTO bDTO) {
		int cnt =0;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.insert("kr.co.sist.user.product.detail.insertBookmark", bDTO);
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//insertBookmark
	//찜하기 다시 누르면 삭제
	public int deleteBookmark(BookmarkDTO bDTO) {
		int cnt =0;
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.delete("kr.co.sist.user.product.detail.deleteBookmark", bDTO);
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//deleteBookmark
	
	//마지막으로 끌올한 날 가져옴
	public LocalDateTime selectUpDate(int pnum) {
		LocalDateTime date = null;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		date=ss.selectOne("kr.co.sist.user.product.detail.selectBumpDate", pnum);
		if( ss != null) { ss.close(); }//end if
		
		return date;
	}//selectUpDate
	//끌올 버튼을 누를 경우 현재 날짜로 변경
		public int updateUpDate(int pnum) {
			int cnt =0;
			SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
			
			cnt=ss.update("kr.co.sist.user.product.detail.updateBumpDate", pnum);
			if( ss != null) { ss.close(); }//end if
			
			return cnt;
		}//updateUpDate
	
	//판매 상태 변경
	public int updateProductStatus(SellStatusDTO ssDTO) {
		int cnt =0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		cnt=ss.update("kr.co.sist.user.product.detail.updateSellStatus", ssDTO);
		if( ss != null) { ss.close(); }//end if
		return cnt;
	}//updateProductStatus
	//판매완료
	public int updateProductOver(int pnum) {
		int cnt =0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.update("kr.co.sist.user.product.detail.updateOverSell", pnum);
		if( ss != null) { ss.close(); }//end if
		return cnt;
	}//updateProductStatus
	
	//발송완료
	public int updateProductSend(int pnum) {
		int cnt =0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.update("kr.co.sist.user.product.detail.updateDeliveryDate", pnum);
		if( ss != null) { ss.close(); }//end if
		return cnt;
	}//updateProductSend
	
	//게시글 삭제
	public int updateDeleteFlag(int pnum) {
		int cnt =0;
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(true);
		
		cnt=ss.update("kr.co.sist.user.product.detail.updateDeleteFlag", pnum);
		if( ss != null) { ss.close(); }//end if
		
		return cnt;
	}//deleteProduct
	
	public String selectBuyerId(int pnum) {
		
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		
		String sellerId = ss.selectOne("kr.co.sist.user.product.detail.selectBuyerId", pnum);
		
		if( ss != null) { ss.close(); }//end if
		
		return sellerId;
	}
}
