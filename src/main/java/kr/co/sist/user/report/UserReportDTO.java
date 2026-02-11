package kr.co.sist.user.report;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserReportDTO {
	private String reporter_id, reportee_id, report_content;
	private String reviewImgFilename;
	private int report_num;
	private int report_code;//신고 유형 코드 
	private Date report_date;
}
