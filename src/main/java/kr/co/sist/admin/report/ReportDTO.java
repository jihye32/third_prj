package kr.co.sist.admin.report;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportDTO {

    // paging
    private int currentPage = 1;
    private int startRow;
    private int endRow;

    // search
    private String keyword;
    private String searchType; // all | reporter | reportee | content
    private Integer reportCode;      // 신고종류 필터
    private Integer reportStateCode; // 처리상태 필터

    // action
    private int reportNum;
    private String answer;
}
