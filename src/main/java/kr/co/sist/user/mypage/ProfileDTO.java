package kr.co.sist.user.mypage;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProfileDTO {
	private MultipartFile profileImg;
	private String intro, existingImage, oldImg, newImg, userId;
	
}
