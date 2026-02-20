package kr.co.sist.user.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatDTO {
	private String content, writerId, otherId, type;
	private Integer roomNum, productNum;
}
