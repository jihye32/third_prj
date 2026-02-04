package kr.co.sist.admin.ask;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AskDTO {

    // detail/answerProcess 용
    private int askNum;
    private String answerText;

    // 검색
    private String searchType; // all | title | user | content
    private String keyword;

    // 페이징
    private int currentPage;
    private int startRow;
    private int endRow;
}
