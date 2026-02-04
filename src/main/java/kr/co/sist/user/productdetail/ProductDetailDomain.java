package kr.co.sist.user.productdetail;

import java.time.LocalDateTime;
import java.util.List;

import kr.co.sist.user.productdetail.enums.DealType;
import kr.co.sist.user.productdetail.enums.ProductStatus;
import kr.co.sist.user.productdetail.enums.SellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//선택된 상품 정보 가져오는 Domain
public class ProductDetailDomain {
	private int productNum, sellerId, price, viewCnt, chatCnt, bookmarkCnt;
	private String category, title, content, thumbnail, dealAddress, deleteFlag;
	private List<String> tag, images;
	private LocalDateTime bumpDate;
	private String timeAgo;
	
	// DB에서 받는 코드값
	private String productStatusCode;
	private int sellStatusCode;
	
	// 서버 내부에서 쓰는 enum
	private ProductStatus productStatus;
	private SellStatus sellStatus;
	private List<DealType> dealType;
	
	public void setProductStatusCode(String code) {
	    this.productStatusCode = code;
	    this.productStatus = ProductStatus.fromCode(code); // ✅ 수정
	}
	
	public void setSellStatusCode(int code) {
	    this.sellStatusCode = code;
	    this.sellStatus = SellStatus.fromCode(code); // ✅ 수정
	}
}
/*
PRODUCT_NUM = productNum : 상품번호
TITLE = title : 상품명(상품 제목)
PRICE = price : 상품가격
PRODUCT_STATUS = productStatus : 상품 상태(U: 중고, N: 새거)
THUMBNAIL = thumbnail : 썸네일 이미지명
CONTENT = content : 상품 설명
UP_DATE = bumpDate : 끌올 날짜
VIEW_COUNT = viewCnt : 조회수
STORE_NUM = sellerId : 판매자 상점 번호
STATUS_CODE = sellStatus : 판매 상태(1: 판매중, 2: 예약중, 3: 판매완료)

CODE_TEXT = category : 카테고리

TAG = tags : 태그(앞에 #붙여주기)

PRODUCT_IMG = images : 상세이미지명

SELL_CODE = dealType : 거래방법(직거래/택배거래)
ADDR = dealAddress : 직거래 장소

count(*) from bookmark = bookmarkCnt : 찜 개수

count(distinct CHATROOM_NUM) = chatCnt : 채팅 개수
*/