package kr.co.sist.admin.product;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminProductDomain {
    
    private int product_num; //상품번호
    private int price; //가격
    private String title; //상품명
    private String addr; //판매희망지역
    private Date input_date; //작성일
    private String product_status; //상품 상태
    private String thumbnail; // 썸네일 이미지명
    private int status_code, delete_text, delete_flag; //판매상태코드, 삭제사유, 삭제플래그
    private String description; // 상세페이지용
    public int getProductNo() { return product_num; }
    public int getPrice() { return price; }
    public String getProductName() { return title; }
    public String getLocation() { return addr; }
    public Date getInputDate() { return input_date; }
    public String getSellState() { return product_status; }
}