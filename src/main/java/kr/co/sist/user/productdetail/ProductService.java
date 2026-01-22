package kr.co.sist.user.productdetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired(required = false)
	ProductDetailDAO pDAO;
	
//////product detail domain에 들어갈 내용/////////////////////////////////////////////////////////////////////
	//선택된 상품 번호로 조회된 상품 정보 조합
	public ProductDetailDomain searchProduct(int pnum) {
		ProductDetailDomain pdd = null;
		pdd = pDAO.selectProduct(pnum);
		if(pdd != null) {
			pdd.setTag(pDAO.selectTag(pnum));
			pdd.setImg(pDAO.selectImg(pnum));
			pdd.setBookmarkCnt(pDAO.cntBookmark(pnum));
			pdd.setChatCnt(pDAO.cntChat(pnum));
		}
		
		return pdd;
	}//searchProduct
	
	//선택된 상품 번호로 조회된 상품 정보 조합
	public SellerDomain searchSeller(String store) {
		SellerDomain sd = new SellerDomain();
		sd.setProductCnt(pDAO.cntSellProduct(store));
		sd.setReivewCnt(pDAO.cntReview(store));
		
		
		return sd;
	}//searchProduct
	
//////게시글 수정사항/////////////////////////////////////////////////////////////////////
	//정상적인 확인을 위해서 boolean를 반환하지만 완성되고는 반환할 필요x
	//같은 아이디가 아닐 경우 조회수 증가
	public boolean modifyViewCnt(int pnum) {
		return pDAO.updateViewCnt(pnum)==1;
	}//updateViewCnt
	
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
	public int updateProductStatus(int pnum) {
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
