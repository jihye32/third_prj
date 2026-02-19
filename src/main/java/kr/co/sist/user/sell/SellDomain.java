package kr.co.sist.user.sell;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SellDomain {
	private int productNum, categoryCode, price, storeNum;
	private MultipartFile[] productImages;
	private String productTitle, content, productStatus, addr;
	private List<Integer> tradeType = new ArrayList<Integer>();
	private String thumbnail;
	private List<String> productImgName = new ArrayList<String>();
	private boolean packageFlag, directFlag;
}