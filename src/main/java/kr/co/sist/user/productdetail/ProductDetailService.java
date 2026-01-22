package kr.co.sist.user.productdetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserProductService")
public class ProductDetailService {

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
	
	public int searchUpDate(int num) {
		int cnt =0;
		//현재 날짜와 select으로 받은 날짜의 차이를 구해 반환
		return cnt;
	}//searchUpDate
	public boolean modifyUpDate(int pnum) {
		return pDAO.updateUpDate(pnum)==1;
	}//modifyUpDate
	
	public boolean modifyProductStatus(int pnum) {
		return pDAO.updateProductStatus(pnum)==1;
	}//modifyProductStatus
	
	public boolean modifyProductDetail(ProductModifyDTO pmDTO) {
		return pDAO.updateProductDetail(pmDTO)==1;
	}//modifyUpDate
	
	public boolean removeProduct(int pnum) {
		return pDAO.deleteProduct(pnum)==1;
	}//removeProduct
}
