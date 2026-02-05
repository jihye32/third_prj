package kr.co.sist.admin.product;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminProductDTO {
    private int category_code;
    private int startNum;
    private int endNum;
    private int currentPage = 1;
    private int totalPage;
    private int pageScale = 15; 
    
 // [에러 해결 포인트] 이 필드와 Getter가 반드시 있어야 합니다!
    private int productNo;      
    private String deleteReason; // [필수] 매퍼의 #{deleteReason}과 이름이 똑같아야 함
    
    private String keyword;     
    private String sortBy = "latest"; 
    private int product_num;

    public void setNumbers() {
        this.startNum = (this.currentPage - 1) * this.pageScale + 1;
        this.endNum = this.startNum + this.pageScale - 1;
    }
}