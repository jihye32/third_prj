package kr.co.sist.admin.member;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberDomain {
    private int memberNo;      // 회원 번호
    private String userId;     // 회원 아이디
    private String name;       // 회원 이름
    private String email;      // 이메일
    private Date inputDate;    // 계정 생성일
}