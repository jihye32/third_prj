package kr.co.sist.admin.report;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportDomain {

    private int reportNum;

    private Integer reportCode;
    private String reportText;       // report_value.report_text

    private String reportContent;
    private Date reportDate;

    private String reporterId;
    private String reporteeId;

    private String answer;
    private Date answerDate;

    private Integer reportStateCode;
    private String reportStateText;  // report_state.report_state_text

    private String reportDeleteflag;
}
