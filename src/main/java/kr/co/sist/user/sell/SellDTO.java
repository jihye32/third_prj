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
public class SellDTO {
	private int productNum, categoryCode, price, storeNum;
	private MultipartFile thumbnail;
	private List<MultipartFile> productImages;
	private String productTitle, content, productStatus, addr;
	private List<String> tradeType = new ArrayList<String>();
	private String thumbnailName;
	private List<String> productImgName = new ArrayList<String>();
}
