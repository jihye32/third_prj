package kr.co.sist.admin.product;

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
    
    private int product_num; 
    private int delete_text; 
    
    private String keyword;
    private String sortBy = "latest";

    public void setNumbers() {
        this.startNum = (this.currentPage - 1) * this.pageScale + 1;
        this.endNum = this.startNum + this.pageScale - 1;
    }

}