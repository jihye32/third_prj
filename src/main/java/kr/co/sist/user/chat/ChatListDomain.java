package kr.co.sist.user.chat;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatListDomain {
	private int roomNum, pnum;
	private String otherId, content, thumbnail,storeName, profile, deleteFlag;
	private Timestamp sendDate;
}
