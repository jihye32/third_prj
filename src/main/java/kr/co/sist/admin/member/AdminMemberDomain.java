package kr.co.sist.admin.member;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberDomain {
    private String user_id;     // 회원 아이디
    private String name;       // 회원 이름
    private String email;      // 이메일
    private Date input_date;    // 계정 생성일
    private String store_name;
    private String delete_flag;
    
    private String suspension_flag;    // 'Y' 또는 'N'
    private Date suspension_end_date;  // 정지 종료일
    
}