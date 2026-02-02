package kr.co.sist.admin.member;

import lombok.Data;

@Data
public class AdminMemberPrdvDomain {
    // --- 상점 정보 (Store) ---
    private int store_num;
    private String store_name;
    private String profile_img;
    private String introduce;
    private String input_date;
    private long open_days; // 계산된 오픈 일수

    // --- 상품 정보 (Product) ---
    private int product_num;
    private String title;
    private int price;
    private String addr;        // 거래지역
    private String p_input_date; // 상품 등록일
    private int status_code;     // 1:판매중, 2:예약중, 3:판매완료
    private String status_text;
    private int bookmark_count;    //

    // --- 후기 정보 (Review) ---
    private String review_text;
    private int review_rate;
    private String review_date;
    private String writer_id;    // 후기 작성자 아이디
}