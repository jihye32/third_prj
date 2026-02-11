package kr.co.sist.user.support;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SupportDomain {
    private int boardNum;        // context_num
    private String boardType;    // 'N' 공지 / 'F' FAQ
    private String title;
    private String content;      // 상세에서만 사용
    private Date writeDate;
    private String adminId;
    private String deleteFlag;   // 'N' / 'Y'
}
