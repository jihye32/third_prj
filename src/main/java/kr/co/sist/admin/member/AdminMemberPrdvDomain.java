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
    private long open_days;

    // --- 상품 정보 (Product) ---
    private int product_num;
    private String title;
    private int price;
    private String addr;        
    private String p_input_date; 
    private int status_code;     
    private String status_text;
    private int bookmark_count;   

    // --- 후기 정보 (Review) ---
    private String review_text;
    private int review_rate;
    private String review_date;
    private String writer_id;   
}