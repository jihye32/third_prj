package kr.co.sist.admin.board;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDomain {

    private int boardNum;        
    private String boardType;    // 'N' 공지 / 'F' FAQ
    private String title;        
    private String content;      
    private Date writeDate;      
    private String adminId;     
    private String deleteFlag;   // 'N' / 'Y'
}
