package kr.co.sist.user.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/notice")
@Controller("userNoticeController")
public class NoticeController {

	@GetMapping("/noticeFrm")
	public String noticeFrm() {
		return "/notice/noticeFrm";
	}// noticeFrm
	
	@GetMapping("/noticeDetailFrm")
	public String noticeDetailFrm() {
		return "/notice/noticeDetailFrm";
	}// noticeDetailFrm
	
}// class
