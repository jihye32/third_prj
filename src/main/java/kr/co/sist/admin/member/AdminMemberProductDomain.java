package kr.co.sist.admin.member;

import lombok.Data;

@Data
public class AdminMemberProductDomain {
    // --- 상점 정보 (Store Profile) ---
    private String user_id;      // USER_ID
    private String store_name;          // 상점명 (COL)
    private String profile_img;  // PROFILE_IMG
    private String introduce;    // INTRODUCE
    private String input_date;   // INPUT_DATE (상점 등록일)
    private long open_days;      // 계산된 오픈 일수

    // --- 거래 내역 (Transaction History) ---
    private int product_num;     // 상품번호
    private String title;        // 상품 이름
    private String trade_date;   // 거래체결일자
    private String target_id;    // 상대방 ID (판매자 혹은 구매자)
    private int price;           // 가격
    private String status_text;
}