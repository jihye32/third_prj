package kr.co.sist.user.chat;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatDomain {
	private String content, writerId, type;
	private int chatNum, roomNum;
	private Timestamp chatDate, readDate;
}
