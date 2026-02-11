package kr.co.sist.user.support;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserAskDomain {
    private int askNum;
    private String askCode;
    private String askTitle;
    private String askText;
    private Date askDate;
    private String userId;

    private String answerText;
    private Date answerDate;

    // 화면용
    private String answerStatus; // 미답변/답변완료
    private String askTypeText;  // ask_value.ask_text

    // 첨부이미지 파일명 리스트
    private List<String> imgList;
}
