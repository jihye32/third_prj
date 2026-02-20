package kr.co.sist.admin.member;

import lombok.Data;

@Data
public class AdminMemberProductDomain {
    // --- 상점 정보 (Store Profile) ---
    private String user_id;     
    private String store_name;     
    private String profile_img; 
    private String introduce;    
    private String input_date;   
    private long open_days;      

    // --- 거래 내역 (Transaction History) ---
    private int product_num;     
    private String title;        
    private String trade_date;   
    private String target_id;    
    private int price;          
    private String status_text;
}