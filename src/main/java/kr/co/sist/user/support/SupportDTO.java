package kr.co.sist.user.support;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SupportDTO {
    private int boardNum;
    private String boardType;   // 'N' / 'F'
    private String deleteFlag;  // 'N' / 'Y'

    // 검색
    private String keyword;
}
