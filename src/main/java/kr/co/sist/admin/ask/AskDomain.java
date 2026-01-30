package kr.co.sist.admin.ask;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskDomain {

    private int askNum;
    private String askCode;
    private String askTitle;
    private String askText;
    private Date askDate;
    private String userId;

    private String answerText;
    private Date answerDate;

    // 화면용 (답변 상태)
    public String getAnswerStatus() {
        return answerText == null ? "답변예정" : "답변완료";
    }
}
