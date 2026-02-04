package kr.co.sist.admin.ask;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AskDomain {

    private int askNum;
    private String askCode;
    private String askTitle;
    private String askText;
    private Date askDate;
    private String userId;

    private String answerText;
    private Date answerDate;

    // 리스트 화면용 (선택)
    private String answerStatus; // "미답변" / "답변완료"
    
    private String askTypeText;  // ask_value.ask_text (결제/환불, 계정/보안, 기타)

}
