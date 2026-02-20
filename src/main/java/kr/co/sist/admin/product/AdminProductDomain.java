package kr.co.sist.admin.product;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminProductDomain {
    // 1. 기본 상품 정보
    private int product_num;
    private String title;
    private int price;
    private String description;
    private String product_status;
    private int status_code;
    private String addr;             
    private String thumbnail;
    private List<String> product_images;
    private String store_name;
    private int category_code;
    private int view_count;
    private int bookmark_count;
    private String user_id;
    
    // 2. 삭제 관련 필드 (에러 해결 핵심)
    private String delete_flag;     
    private String delete_text;       
    
    // 3. 날짜 및 시간 계산 필드
    private Date input_date;
    private Date modify_date;
    private String time_string;     

    // 4. 거래 정보 필드 (매퍼 조인 결과용)
    private String order_id;         
    private String buyer_id;          
    private String order_status;      
    private Date shipped_date;      
    private Date delivered_date;   
    private String destination_addr;    
    private String destination_detail;  

    /**
     * [Thymeleaf SpEL 에러 방지용 수동 Getter]
     * p.delete_flag 호출 시 Lombok이 생성한 이름과 충돌하지 않도록 명시적으로 정의합니다.
     */
    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    public Date getModify_date() { return modify_date; }
    public Date getInput_date() { return input_date; }
    public int getProductNo() { return product_num; }
}