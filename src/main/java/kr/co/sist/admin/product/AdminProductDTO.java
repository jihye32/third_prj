package kr.co.sist.admin.product;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminProductDTO {
    private int category; // 0이면 전체
    private int startNum;
    private int endNum;
    private int currentPage = 1;
    private int totalPage;
    private int pageScale = 15; // 20개씩 출력
    
    private String keyword;     
    private String sortBy = "latest"; // 기본값 최신순
    private int productNo; // 상세조회용

    public void setNumbers() {
        this.startNum = (this.currentPage - 1) * this.pageScale + 1;
        this.endNum = this.startNum + this.pageScale - 1;
    }
}