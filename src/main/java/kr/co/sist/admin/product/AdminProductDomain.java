package kr.co.sist.admin.product;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminProductDomain {
    
    private int productNo;
    private int price;
    private String productName;
    private String location;
    private Date inputDate;
    private String sellState;
    private String thumbnail;
    private int sellStateCode, deleteReason, deleteState;
    private String description; // 상세페이지용
    public int getProductNo() { return productNo; }
    public int getPrice() { return price; }
    public String getProductName() { return productName; }
    public String getLocation() { return location; }
    public Date getInputDate() { return inputDate; }
    public String getSellState() { return sellState; }
}