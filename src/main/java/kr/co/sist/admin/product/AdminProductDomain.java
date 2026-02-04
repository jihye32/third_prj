package kr.co.sist.admin.product;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AdminProductDomain {
    private int product_num;
    private String title;
    private int price;
    private String description;
    private String product_status;
    private int status_code;
    private String addr;              /* 발송지 (Origin) */
    private String thumbnail;
    private String store_name;
    private int category_code;
    private int view_count;
    private int bookmark_count;
    private Date input_date;
    private Date modify_date;
    private String time_string;

    // 거래 정보 필드
    private String buyer_id;          /* 구매자 ID */
    private String destination_addr;    /* 수령지 주소 */
    private String destination_detail;  /* 수령지 상세주소 */

    public int getProductNo() { return product_num; }
}