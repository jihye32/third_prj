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
    private String addr;              /* 발송지 (Origin) */
    private String thumbnail;
    private List<String> product_images;
    private String store_name;
    private int category_code;
    private int view_count;
    private int bookmark_count;
    private String user_id;
    
    // 2. [추가] 삭제 관련 필드 (에러 해결 핵심)
    private String delete_flag;       /* 삭제 여부 (Y/N) */
    private String delete_text;       /* 삭제 사유 */
    
    // 3. 날짜 및 시간 계산 필드
    private Date input_date;
    private Date modify_date;
    private String time_string;       /* "방금 전", "1일 전" 등 */

    // 4. [보강] 거래 정보 필드 (매퍼 조인 결과용)
    private String order_id;          /* 주문 번호 (po.ORDER_ID) */
    private String buyer_id;          /* 구매자 ID (po.BUYER_ID) */
    private String order_status;      /* 배송 상태 (po.ORDER_STATUS) */
    private Date shipped_date;        /* 발송 날짜 */
    private Date delivered_date;      /* 도착 날짜 */
    private String destination_addr;    /* 수령지 주소 (ad.address) */
    private String destination_detail;  /* 수령지 상세주소 (ad.address_detail) */

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

    // 서비스 레이어에서 사용하는 Getter들 (에러 방지용)
    public Date getModify_date() { return modify_date; }
    public Date getInput_date() { return input_date; }
    public int getProductNo() { return product_num; }
}