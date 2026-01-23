package kr.co.sist.admin.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {

    private int boardNum;
    private String boardType;   // 'N' / 'F'
    private String title;
    private String content;
    private String adminId;
    private String deleteFlag;

    // 검색용
    private String keyword;
    private String searchType;  

    // 화면 구분용
    private String type;        // FAQ / NOTICE

    // 페이징
    private int currentPage;
    private int startRow;
    private int endRow;
}
