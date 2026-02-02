package kr.co.sist.admin.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberDetailDomain {
    private String user_id;      // 아이디
    private String col;    // 상점이름
    private String name;        // 이름
    private String birthday;   // 생년월일 (2000.01.01 형식)
    private String email;       // 이메일
    
    private long open_days;
}