package kr.co.sist.admin.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberDetailDomain {
    private String userId;      // 아이디
    private String nickname;    // 닉네임
    private String name;        // 이름
    private String birthdate;   // 생년월일 (2000.01.01 형식)
    private String email;       // 이메일
}