package kr.co.sist.user.chat;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatListDomain {
	private int roomNum, pnum;
	private String otherId, content;
	private Timestamp sendDate;
}
