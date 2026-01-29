package kr.co.sist.user.productdetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("UserProductDetailDAO")
public class ProductDetailDAO {
//////product detail domain에 들어갈 내용/////////////////////////////////////////////////////////////////////
	//선택된 상품 번호로 상품의 정보 조회
	public ProductDetailDomain selectProduct(int pnum) {
		ProductDetailDomain pdd = null;
		
		
		return pdd;
	}//selectProduct
	
	//선택된 상품 번호에 등록한 태그들 가져옴
	public List<String> selectTag(int pnum){
		List<String> list = new ArrayList<String>();
		
		return list;
	}//selectTag
	
	//선택된 상품 번호에 등록한 이미지들 가져옴
	public List<String> selectImg(int pnum){
		List<String> list = new ArrayList<String>();
		
		return list;
	}//selectImg
	
	//상점명으로 판매 등록한 모든 상품의 개수 가져옴
	public int cntBookmark(int pnum) {
		int cnt =0;
		
		return cnt;
	}//cntSellProduct
	
	//상점명으로 판매 등록한 모든 상품의 개수 가져옴
	public int cntChat(int pnum) {
		int cnt =0;
		
		return cnt;
	}//cntSellProduct
	
	//발송완료 확인
	public String selectSendFlag(int pnum) {
		String flag = null;
		
		return flag;
	}//selectSendFlag

	
//////seller domain에 들어갈 내용/////////////////////////////////////////////////////////////////////
	//상점명으로 판매 등록한 모든 상품의 개수 가져옴
	public int cntSellProduct(int ssnum) {
		int cnt =0;
		
		return cnt;
	}//cntSellProduct
	
	//상점명으로 등록되어있는 리뷰의 개수 가져옴
	public int cntReview(int ssnum) {
		int cnt =0;
		
		return cnt;
	}//cntReview
	
	public SellerDomain selectSellerInfo(int ssnum) {
		SellerDomain sd = null;
		
		return sd;
	}//selectSellerProfile
	
	
//////게시글 수정사항/////////////////////////////////////////////////////////////////////
	//정상적인 확인을 위해서 boolean를 반환하지만 완성되고는 반환할 필요x
	//같은 아이디가 아닐 경우 조회수 증가
	public int updateViewCnt(int pnum) {
		int cnt =0;
		
		return cnt;
	}//updateViewCnt
	
	//찜하기 했는지 확인
	public String selectBookmark(int pnum, int snum) {
		String flag = "";
		return flag;
	}//selectBookmark
	//끌올 버튼을 누를 경우 현재 날짜로 변경
	public int insertBookmark(int pnum, int snum) {
		int cnt =0;
			
		return cnt;
	}//addBookmark
	//끌올 버튼을 누를 경우 현재 날짜로 변경
	public int deleteBookmark(int pnum, int snum) {
		int cnt =0;
			
		return cnt;
	}//deleteBookmark
	
	//마지막으로 끌올한 날 가져옴
	public Date selectUpDate(int pnum) {
		Date date = null;
		
		return date;
	}//selectUpDate
	//끌올 버튼을 누를 경우 현재 날짜로 변경
		public int updateUpDate(int pnum) {
			int cnt =0;
			
			return cnt;
		}//updateUpDate
	
	//판매 상태 변경
	public int updateProductStatus(SellStatusDTO ssDTO) {
		int cnt =0;
		
		return cnt;
	}//updateProductStatus
	
	//게시글 수정
	public int updateProductDetail(ProductModifyDTO pmDTO) {
		int cnt =0;
		
		return cnt;
	}//updateProductDetail
	
	//게시글 삭제
	public int deleteProduct(int pnum) {
		int cnt =0;
		
		return cnt;
	}//deleteProduct
}
