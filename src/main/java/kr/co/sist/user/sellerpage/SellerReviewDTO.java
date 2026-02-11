package kr.co.sist.user.sellerpage;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor@ToString
public class SellerReviewDTO {

private String reviewerId;	
private String store_name;
private String writerProfile;
private int product_num;
private int count;
private String writerNickname;
private String review_text;
private Date review_date;
private int review_rate;//별점 숫자


}
