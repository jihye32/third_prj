package kr.co.sist.admin.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminMemberDTO {
    private int startNum;
    private int endNum;
    private int currentPage = 1;
    private int pageScale = 10; // 회원 리스트는 보통 10개씩 출력
    private String keyword;     // 아이디 검색용

    public void setNumbers() {
        this.startNum = (this.currentPage - 1) * this.pageScale + 1;
        this.endNum = this.startNum + this.pageScale - 1;
    }
}