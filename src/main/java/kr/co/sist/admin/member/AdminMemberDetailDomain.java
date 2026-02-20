package kr.co.sist.admin.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberDetailDomain {
    private String user_id;     
    private String store_name;  
    private String name;      
    private String birthday;   
    private String email;      
    
    private long open_days;
    
    private String suspension_flag;    
    private java.util.Date suspension_end_date;
}