package kr.co.sist.user.support;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserAskDTO {
    private int askNum;
    private String userId;

    private String askCode;
    private String askTitle;
    private String askText;

    // 다중 업로드
    private List<MultipartFile> files;
}
