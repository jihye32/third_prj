package kr.co.sist.user.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatListDTO {
	private Integer pnum;
	private String lowId, highId, content;
}
