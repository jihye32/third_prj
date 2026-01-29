package kr.co.sist.user.sellerpage;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReportDTO {
	private String reporterId, reporteeId, reportType, reportDetail;
	private List<String> ReportImgNameList;
}
