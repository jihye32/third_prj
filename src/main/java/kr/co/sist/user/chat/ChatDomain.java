package kr.co.sist.user.chat;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDomain {
	private String content, writerId;
	private int chatNum, roomNum;
	private Timestamp chatDate, readDate;
}
