package kr.co.sist.user.productdetail;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.productdetail.enums.DealType;

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
			pdd.setImages(pDAO.selectImg(pnum));
			List<DealType> type = new ArrayList<DealType>();
			List<Integer> typeCode = pDAO.selectDealType(pnum);
			for(int code : typeCode) {
				type.add(DealType.fromCode(code));
				if(code == 1) {
					pdd.setDealAddress(pDAO.selectAddress(pnum));
				}
			}
			pdd.setDealType(type);
			pdd.setBookmarkCnt(pDAO.cntBookmark(pnum));
			pdd.setChatCnt(pDAO.cntChat(pnum));
//			pdd.setSendFlag(pDAO.selectSendFlag(pnum));
		}
		
		return pdd;
	}//searchProduct
	
	//판매자 정보 조합
	public SellerInfoDomain searchSeller(int sid) {
		SellerInfoDomain sd = new SellerInfoDomain();
		sd=pDAO.selectSellerInfo(sid);
		sd.setProductCnt(pDAO.cntSellProduct(sid));
		sd.setReivewCnt(pDAO.cntReview(sid));
		
		return sd;
	}//searchProduct
	
	//발송완료 확인
	public boolean searchSendFlag(int pnum) {
		boolean flag = false;
		OrderDomain od = pDAO.selectSendFlag(pnum);
		if(od == null) return false;
		if(od.getDeliveredDate() == null) flag= true;
		return flag;
	}//searchSendFlag
	
	
//////게시글 수정사항/////////////////////////////////////////////////////////////////////
	//정상적인 확인을 위해서 boolean를 반환하지만 완성되고는 반환할 필요x
	//같은 아이디가 아닐 경우 조회수 증가
	public boolean modifyViewCnt(int pnum) {
		return pDAO.updateViewCnt(pnum)==1;
	}//updateViewCnt
	
	public boolean searchUpDate(int num) {
		boolean check =false;
		//현재 날짜와 select으로 받은 날짜의 차이를 구해 반환
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime bumpAt = pDAO.selectUpDate(num);
		long days = ChronoUnit.DAYS.between(bumpAt, now);
        if(days > 1) check = true;
        
		return check;
	}//searchUpDate
	public boolean modifyUpDate(int pnum) {
		return pDAO.updateUpDate(pnum)==1;
	}//modifyUpDate
	
	public boolean modifyProductStatus(SellStatusDTO ssDTO) {
		return pDAO.updateProductStatus(ssDTO)==1;
	}//modifyProductStatus
	public boolean modifyProductOver(int pnum) {
		return pDAO.updateProductOver(pnum)==1;
	}//modifyProductOver
	public boolean modifyProductSend(int pnum) {
		return pDAO.updateProductSend(pnum)==1;
	}//modifyProductOver
	
	public boolean modifyDeleteFlag(int pnum) {
		return pDAO.updateDeleteFlag(pnum)==1;
	}//removeProduct
	
	//북마크 처리
	public String searchBookmark(BookmarkDTO bDTO) {
		return pDAO.selectBookmark(bDTO);
	}//searchBookmark
	public boolean addBookmark(BookmarkDTO bDTO) {
		return pDAO.insertBookmark(bDTO)==1;
	}//addBookmark
	public boolean removeBookmark(BookmarkDTO bDTO) {
		return pDAO.deleteBookmark(bDTO)==1;
	}//removeBookmark
	
	public String searchBuyerId(int pnum) {
		return pDAO.selectBuyerId(pnum);
	}
}
