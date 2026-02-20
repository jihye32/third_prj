package kr.co.sist.admin.member;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberDomain {
    private String user_id;     
    private String name;       
    private String email;     
    private Date input_date;    
    private String store_name;
    private String delete_flag;
    
    private String suspension_flag;   
    private Date suspension_end_date;
    
}