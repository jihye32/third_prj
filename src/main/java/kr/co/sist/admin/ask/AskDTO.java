package kr.co.sist.admin.ask;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskDTO {

    private int askNum;
    private String askCode;
    private String askTitle;
    private String askText;
    private String userId;

    private String answerText;

    // 검색
    private String keyword;
    private String searchType;

    // 페이징
    private int currentPage;
    private int startRow;
    private int endRow;
}
